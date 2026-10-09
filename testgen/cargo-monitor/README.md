# Test generation - cargo-monitor

OVERNIGHT_PROVISIONAL - the matrix is approved_provisional. A human must confirm it.

| Case | Feature | What it pins |
| --- | --- | --- |
| cargo-monitor-001-C001 | cargo-monitor-001 | GET /rest/cargo status, media type, body, and key order for the four sample cargos |

Traceability: testgen/cargo-monitor/TRACEABILITY.yaml
Harness: tests/characterization/harness/ct_harness.py (case list: tests/characterization/cargo-monitor/cases.json)

Deferred: the empty repository case. The sample loader always runs at startup.
