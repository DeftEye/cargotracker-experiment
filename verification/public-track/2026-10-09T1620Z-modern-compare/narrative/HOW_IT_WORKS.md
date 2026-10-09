# How it works - public-track-001

Pack: cargotracker-springboot3-strangler@1 (overnight-provisional bind)
Conversion PR: https://github.com/DeftEye/cargotracker-experiment/pull/4
Evidence: verification/public-track/2026-10-09T1620Z-modern-compare/evidence/REPORT.md
Supersedes: verification/public-track/2026-10-09T1600Z-modern-compare (6 cases, all MATCH)

The JSF bean Track and the Facelets page public/track.xhtml moved to a Spring MVC controller (TrackController) and a Thymeleaf template (track.html) in modern/.
The URL and the form field name stay the same. CargoTrackingView keeps the legacy status, ETA, and event text rules and the same date format.

## Result

COMPARE: 6 of 7 pinned cases match. Verdict: FAIL (PARITY_FAIL) on public-track-001-C007.

## The failing case, in plain words

In a browser, the legacy tracking box is an autocomplete that only accepts ids from its list.
If you type an unknown id, the box clears it before the form posts. The form then posts an empty id.
The empty id fails the required check. The bean is view scoped, so the page still shows the cargo from the previous search, and the box turns red.
Modern has no view state. An empty post shows no result and no red box.

The first verification run did not see this, because each case started a new page.
The screen recording of the side-by-side demo showed it. Case C007 now pins it on legacy.

## Decision for a human

- Copy the legacy behaviour in modern (Conversion follow-up PR), or
- Accept the difference as intentional and write a waiver for C007.

## Not verified

- The browser-side autocomplete itself (client JavaScript).
- Behaviour outside the pinned cases (see testgen/public-track/TRACEABILITY.yaml).
- Performance, security, and load.
