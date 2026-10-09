# Conversion plan (read-only artefact)

Pack: cargotracker-springboot3-strangler@1 (BOUND, overnight-provisional)
Batch: first_conversion_batch = [cargo-monitor-001, public-track-001]
Mid-gate: mandatory by rule (first conversion on this pack, known_risks touched). Under the charter, the run records the plan and continues. A human must review this plan with the PRs.

| Order | Feature | Boundary | Legacy symbols | Modern paths | Mapping rules | Pins |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | cargo-monitor-001 | GET /cargo-tracker/rest/cargo | CargoMonitoringService.getAllCargo; Delivery; Itinerary; RouteSpecification; HandlingHistory; SampleDataGenerator | modern/pom.xml; modern/src/main/java/net/java/cargotracker/modern/{domain,repository,seed}/**; web/CargoMonitoringController.java | JAX-RS to @RestController (ordered map); JPA entity to plain class; JPA repository to in-memory; @Startup loader to Spring component; Validate to Objects | tests/characterization/cargo-monitor/cases/cargo-monitor-001/C001.approved.json |
| 2 | public-track-001 | /cargo-tracker/public/track.xhtml (GET + POST trackingForm:trackingId_input) | Track; CargoTrackingViewAdapter; public/track.xhtml; templates/common/public.xhtml | web/TrackController.java; web/CargoTrackingView.java; resources/templates/track.html; resources/static/** | JSF bean + Facelets to @Controller + Thymeleaf; view adapter to plain view class | tests/characterization/public-track/cases/public-track-001/C001..C006.approved.json |

Feature 2 depends on the domain core from feature 1. The PR for feature 2 stacks on the PR for feature 1.

Known risks and how the plan covers them:
- Time zone: modern runs with -Duser.timezone=UTC (surefire and run command).
- HandlingEvent equality: ported field for field (cargo, voyage, completion time, location, type).
- Itinerary.isExpected: equals for RECEIVE, UNLOAD, CLAIM; sameIdentityAs for LOAD. Ported as is.
- MNO456 MISROUTED: the seed keeps the legacy deadline string "2016-3-27" and parses it leniently.
- CDDL: ported code carries the legacy behaviour; the licence notice must go with the modern module before any release.
