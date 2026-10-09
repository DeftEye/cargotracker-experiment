# How it works - public-track-001

Pack: cargotracker-springboot3-strangler@1 (overnight-provisional bind)
Conversion PR: https://github.com/DeftEye/cargotracker-experiment/pull/4
Evidence: verification/public-track/2026-10-09T1600Z-modern-compare/evidence/REPORT.md

The JSF bean Track and the Facelets page public/track.xhtml moved to a Spring MVC controller (TrackController) and a Thymeleaf template (track.html) in modern/.
The URL and the form field name stay the same. The view class CargoTrackingView keeps the legacy status, ETA, and event text rules and the same date format.
Legacy quirks stay: an unknown id shows no result and no message, and the next expected activity text does not show.

Try it: open both pages and enter ABC123, JKL567, DEF789, MNO456, or an unknown id.
- Legacy: http://localhost:8080/cargo-tracker/public/track.xhtml
- Modern: http://localhost:8081/cargo-tracker/public/track.xhtml

## Result

COMPARE: 6 of 6 pinned cases match the legacy goldens after the same scrub.
Verdict: BLOCKED (GOLDENS_PENDING_HUMAN_APPROVE). PARITY=GREEN needs a human to approve the goldens and re-BIND the pack.

## Not verified

- Behaviour outside the pinned cases (see the deferred list in testgen/public-track/TRACEABILITY.yaml).
- Performance, security, and load.
- Data written by other slices. Both apps load fixed sample data at startup.
