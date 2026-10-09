#!/usr/bin/env python3
"""Characterization harness for the cargotracker pathfinder slices.

RECORD and REPLAY run against legacy only. COMPARE runs against modern and
treats goldens as read-only. Only RECORD writes *.approved.json files.
"""
import argparse
import datetime
import hashlib
import html
import http.cookiejar
import json
import os
import re
import sys
import urllib.error
import urllib.parse
import urllib.request
from html.parser import HTMLParser

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", "..", ".."))
CHAR_ROOT = os.path.join(ROOT, "tests", "characterization")
SCRUB_PROFILE = "ws-collapse-v1"


def norm(text):
    return re.sub(r"\s+", " ", html.unescape(text).replace("\xa0", " ")).strip()


class TrackResultParser(HTMLParser):
    """Collects the observable parts of the public track page."""

    def __init__(self):
        super().__init__(convert_charrefs=True)
        self.depth = 0
        self.in_result = False
        self.result_present = False
        self.paragraphs = []
        self.events = []
        self._p = None
        self._event = None
        self.history_header = False
        self._strong_text = None

    def handle_starttag(self, tag, attrs):
        attrs = dict(attrs)
        if not self.in_result:
            if tag == "div" and attrs.get("id") == "result":
                self.in_result = True
                self.result_present = True
                self.depth = 1
            return
        if tag == "div":
            self.depth += 1
        if tag == "p":
            self._p = []
        elif tag == "i":
            classes = attrs.get("class", "").split()
            if "fa-check" in classes or "fa-flag" in classes:
                self._close_event()
                self._event = {"icon": "check" if "fa-check" in classes else "flag", "text": []}
        elif tag == "br":
            self._close_event()
        elif tag == "strong" and self._p is None:
            self._strong_text = []

    def handle_startendtag(self, tag, attrs):
        self.handle_starttag(tag, attrs)

    def handle_endtag(self, tag):
        if not self.in_result:
            return
        if tag == "p" and self._p is not None:
            self.paragraphs.append(norm("".join(self._p)))
            self._p = None
        elif tag == "strong" and self._strong_text is not None:
            if norm("".join(self._strong_text)) == "Handling History":
                self.history_header = True
            self._strong_text = None
        elif tag == "div":
            self.depth -= 1
            if self.depth == 0:
                self._close_event()
                self.in_result = False

    def handle_data(self, data):
        if not self.in_result:
            return
        if self._p is not None:
            self._p.append(data)
        if self._strong_text is not None:
            self._strong_text.append(data)
        if self._event is not None:
            self._event["text"].append(data)

    def _close_event(self):
        if self._event is not None:
            self.events.append({"icon": self._event["icon"], "text": norm("".join(self._event["text"]))})
            self._event = None


def observe_track(html_body, status):
    parser = TrackResultParser()
    parser.feed(html_body)
    visible = norm(re.sub(r"<script.*?</script>", " ", html_body, flags=re.S))
    return {
        "http_status": status,
        "result_present": parser.result_present,
        "paragraphs": parser.paragraphs,
        "history_header": parser.history_header,
        "events": parser.events,
        "shows_not_found_text": "not found" in re.sub(r"<[^>]+>", " ", visible).lower(),
        "shows_next_expected_activity": "next expected activity" in visible.lower(),
    }


def key_orders(value):
    if isinstance(value, list):
        return [key_orders(v) for v in value]
    if isinstance(value, dict):
        return list(value.keys())
    return None


def run_rest_get(base_url, case):
    req = urllib.request.Request(base_url + case["path"], headers={"Accept": "application/json"})
    try:
        with urllib.request.urlopen(req, timeout=30) as resp:
            status, ctype, raw = resp.status, resp.headers.get("Content-Type", ""), resp.read()
    except urllib.error.HTTPError as e:
        status, ctype, raw = e.code, e.headers.get("Content-Type", ""), e.read()
    body = json.loads(raw.decode("utf-8")) if raw else None
    return {
        "http_status": status,
        "content_type": ctype.split(";")[0].strip().lower(),
        "body": body,
        "key_order": key_orders(body),
    }


def run_track_post(base_url, case):
    jar = http.cookiejar.CookieJar()
    opener = urllib.request.build_opener(urllib.request.HTTPCookieProcessor(jar))
    url = base_url + case["path"]
    with opener.open(url, timeout=30) as resp:
        page = resp.read().decode("utf-8")
    fields = {
        "trackingForm": "trackingForm",
        "trackingForm:trackingId_input": case["tracking_id_input"],
    }
    for name in ("javax.faces.ViewState", "javax.faces.ClientWindow"):
        m = re.search(r'name="%s"[^>]*value="([^"]*)"' % re.escape(name), page)
        if m:
            fields[name] = html.unescape(m.group(1))
    button = re.search(r'<button[^>]*name="(trackingForm:[^"]+)"', page)
    if button:
        fields[button.group(1)] = ""
    data = urllib.parse.urlencode(fields).encode("utf-8")
    try:
        with opener.open(urllib.request.Request(url, data=data), timeout=30) as resp:
            status, body = resp.status, resp.read().decode("utf-8")
    except urllib.error.HTTPError as e:
        status, body = e.code, e.read().decode("utf-8", "replace")
    return observe_track(body, status)


RUNNERS = {"rest_get": run_rest_get, "track_post": run_track_post}


def golden_path(slice_id, case):
    return os.path.join(CHAR_ROOT, slice_id, "cases", case["feature_id"], case["case_id"].split("-")[-1] + ".approved.json")


def sha256_file(path):
    with open(path, "rb") as f:
        return hashlib.sha256(f.read()).hexdigest()


def diff(expected, actual, path="$"):
    if type(expected) is not type(actual):
        return ["%s: expected %r, actual %r" % (path, expected, actual)]
    if isinstance(expected, dict):
        out = []
        for k in sorted(set(expected) | set(actual)):
            if k not in actual:
                out.append("%s.%s: missing in actual" % (path, k))
            elif k not in expected:
                out.append("%s.%s: not in golden" % (path, k))
            else:
                out.extend(diff(expected[k], actual[k], "%s.%s" % (path, k)))
        return out
    if isinstance(expected, list):
        out = []
        if len(expected) != len(actual):
            out.append("%s: length expected %d, actual %d" % (path, len(expected), len(actual)))
        for i, (e, a) in enumerate(zip(expected, actual)):
            out.extend(diff(e, a, "%s[%d]" % (path, i)))
        return out
    return [] if expected == actual else ["%s: expected %r, actual %r" % (path, expected, actual)]


def write_json(path, value):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w") as f:
        json.dump(value, f, indent=2, sort_keys=False)
        f.write("\n")


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--slice", required=True)
    ap.add_argument("--mode", required=True, choices=["RECORD", "REPLAY", "COMPARE"])
    ap.add_argument("--target", required=True, choices=["legacy", "modern"])
    ap.add_argument("--base-url", required=True)
    ap.add_argument("--run-id")
    ap.add_argument("--out-dir", help="COMPARE evidence directory (default verification/<slice>/<run_id>/evidence)")
    args = ap.parse_args()

    if args.mode in ("RECORD", "REPLAY") and args.target != "legacy":
        sys.exit("REFUSED: %s runs against legacy only. Use COMPARE for modern." % args.mode)
    if args.mode == "COMPARE" and args.target != "modern":
        sys.exit("REFUSED: COMPARE runs against modern only.")

    now = datetime.datetime.now(datetime.timezone.utc)
    run_id = args.run_id or now.strftime("%Y-%m-%dT%H%MZ") + "-%s-%s" % (args.target, args.mode.lower())
    with open(os.path.join(CHAR_ROOT, args.slice, "cases.json")) as f:
        cases = json.load(f)["cases"]

    if args.mode == "COMPARE":
        run_dir = args.out_dir or os.path.join(ROOT, "verification", args.slice, run_id, "evidence")
    else:
        run_dir = os.path.join(CHAR_ROOT, args.slice, "runs", run_id)

    results = []
    for case in cases:
        actual = RUNNERS[case["kind"]](args.base_url, case)
        gpath = golden_path(args.slice, case)
        rel_golden = os.path.relpath(gpath, ROOT)
        actual_path = os.path.join(run_dir, "actuals", case["case_id"] + ".json")
        write_json(actual_path, actual)
        row = {
            "case_id": case["case_id"],
            "feature_id": case["feature_id"],
            "golden_path": rel_golden,
            "actual_path": os.path.relpath(actual_path, ROOT),
        }
        if args.mode == "RECORD":
            write_json(gpath, actual)
            replay = RUNNERS[case["kind"]](args.base_url, case)
            problems = diff(actual, replay)
            row["status"] = "REPLAY_GREEN" if not problems else "FAILED"
            row["failure_class"] = None if not problems else "FLAKE"
        else:
            if not os.path.exists(gpath):
                row.update(status="BLOCKED", failure_class=None, failure={"actual_summary": "golden missing"})
                results.append(row)
                continue
            with open(gpath) as f:
                golden = json.load(f)
            problems = diff(golden, actual)
            if args.mode == "REPLAY":
                row["status"] = "REPLAY_GREEN" if not problems else "FAILED"
                row["failure_class"] = None if not problems else "BEHAVIOURAL_DELTA"
            else:
                row["status"] = "MATCH" if not problems else "MISMATCH"
                row["failure_class"] = None if not problems else "PARITY_FAIL"
                if problems:
                    write_json(os.path.join(run_dir, "diffs", case["case_id"] + ".json"),
                               {"golden": rel_golden, "differences": problems})
        row["golden_sha256"] = sha256_file(gpath)
        if problems:
            row["failure"] = {"actual_summary": "; ".join(problems[:5])}
        results.append(row)

    summary = {
        "run_id": run_id,
        "slice_id": args.slice,
        "mode": args.mode,
        "target": args.target,
        "base_url": args.base_url,
        "scrub_profile": SCRUB_PROFILE,
        "goldens_read_only": args.mode != "RECORD",
        "started_at": now.isoformat(),
        "cases": results,
    }
    write_json(os.path.join(run_dir, "results.json"), summary)

    ok_status = {"RECORD": "REPLAY_GREEN", "REPLAY": "REPLAY_GREEN", "COMPARE": "MATCH"}[args.mode]
    lines = ["# %s %s - %s" % (args.mode, args.target, args.slice), "",
             "Run: %s" % run_id, "Base URL: %s" % args.base_url, "Scrub profile: %s" % SCRUB_PROFILE, "",
             "| Case | Feature | Status | Golden sha256 |", "| --- | --- | --- | --- |"]
    for r in results:
        lines.append("| %s | %s | %s | %s |" % (r["case_id"], r["feature_id"], r["status"], r.get("golden_sha256", "-")[:12]))
    failed = [r for r in results if r["status"] != ok_status]
    lines += ["", "Result: %d of %d cases %s." % (len(results) - len(failed), len(results), ok_status)]
    for r in failed:
        lines.append("- %s: %s" % (r["case_id"], r.get("failure", {}).get("actual_summary", "")))
    with open(os.path.join(run_dir, "REPORT.md"), "w") as f:
        f.write("\n".join(lines) + "\n")
    print("\n".join(lines))
    sys.exit(1 if failed else 0)


if __name__ == "__main__":
    main()
