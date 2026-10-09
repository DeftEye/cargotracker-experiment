# Test generation - public-track

OVERNIGHT_PROVISIONAL - the matrix is approved_provisional. A human must confirm it.

| Case | Input | What it pins |
| --- | --- | --- |
| public-track-001-C001 | ABC123 | In port status, ETA date, three expected events |
| public-track-001-C002 | JKL567 | On board status, ETA "?", misdirected line, one flagged event |
| public-track-001-C003 | DEF789 | Not received, no history block |
| public-track-001-C004 | MNO456 | Claimed, customs line, five expected events |
| public-track-001-C005 | NOPE99 | No result block and no visible not-found text |
| public-track-001-C006 | "  ABC123  " | Trim gives the same result as C001 |

Each case also pins that the next expected activity text does not show.

Traceability: testgen/public-track/TRACEABILITY.yaml
Harness: tests/characterization/harness/ct_harness.py (case list: tests/characterization/public-track/cases.json)

Deferred: the map panel, the empty id path, and the autocomplete list.
