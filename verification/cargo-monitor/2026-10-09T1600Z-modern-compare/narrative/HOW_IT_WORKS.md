# How it works - cargo-monitor-001

Pack: cargotracker-springboot3-strangler@1 (overnight-provisional bind)
Conversion PR: https://github.com/DeftEye/cargotracker-experiment/pull/3
Evidence: verification/cargo-monitor/2026-10-09T1600Z-modern-compare/evidence/REPORT.md

The legacy JAX-RS resource CargoMonitoringService moved to a Spring @RestController (CargoMonitoringController) in modern/.
The controller reads every cargo from an in-memory repository and writes the same seven keys in the same order.
Delivery values are not stored. Modern derives them at startup from the same raw sample inputs as legacy (SampleDataLoader), with a port of Delivery, Itinerary, RouteSpecification, and HandlingHistory.

Try it:
- Legacy: curl http://localhost:8080/cargo-tracker/rest/cargo
- Modern: curl http://localhost:8081/cargo-tracker/rest/cargo

## Result

COMPARE: 1 of 1 pinned cases match the legacy goldens after the same scrub.
Verdict: BLOCKED (GOLDENS_PENDING_HUMAN_APPROVE). PARITY=GREEN needs a human to approve the goldens and re-BIND the pack.

## Not verified

- Behaviour outside the pinned cases (see the deferred list in testgen/cargo-monitor/TRACEABILITY.yaml).
- Performance, security, and load.
- Data written by other slices. Both apps load fixed sample data at startup.
