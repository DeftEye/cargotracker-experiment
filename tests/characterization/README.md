# Characterization suite (cargotracker pathfinder)

Goldens are legacy output. Goldens have `golden_status: overnight_pending_human_approve` until a human approves them.

## Run

Legacy must run at http://localhost:8080/cargo-tracker with JVM time zone UTC.

```bash
# RECORD (legacy only; writes *.approved.json)
python3 tests/characterization/harness/ct_harness.py --slice public-track --mode RECORD --target legacy --base-url http://localhost:8080/cargo-tracker

# REPLAY (legacy only; goldens read-only)
python3 tests/characterization/harness/ct_harness.py --slice public-track --mode REPLAY --target legacy --base-url http://localhost:8080/cargo-tracker

# COMPARE (modern only; goldens read-only; Verification owns this mode)
python3 tests/characterization/harness/ct_harness.py --slice public-track --mode COMPARE --target modern --base-url http://localhost:8081/cargo-tracker
```

The harness refuses RECORD or REPLAY against modern, and COMPARE against legacy.
