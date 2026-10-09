# COMPARE modern - public-track

Run: 2026-10-09T1620Z-modern-compare
Base URL: http://localhost:8081/cargo-tracker
Scrub profile: ws-collapse-v1

| Case | Feature | Status | Golden sha256 |
| --- | --- | --- | --- |
| public-track-001-C001 | public-track-001 | MATCH | 6b4a402c675e |
| public-track-001-C002 | public-track-001 | MATCH | 72012c928657 |
| public-track-001-C003 | public-track-001 | MATCH | f65d22e883fc |
| public-track-001-C004 | public-track-001 | MATCH | d8faf4c19f7d |
| public-track-001-C005 | public-track-001 | MATCH | 5808f6a68e5c |
| public-track-001-C006 | public-track-001 | MATCH | 6b4a402c675e |
| public-track-001-C007 | public-track-001 | MISMATCH | f458bc553cb8 |

Result: 6 of 7 cases MATCH.
- public-track-001-C007: $.events: length expected 4, actual 0; $.history_header: expected True, actual False; $.input_marked_error: expected True, actual False; $.paragraphs: length expected 3, actual 0; $.result_present: expected True, actual False
