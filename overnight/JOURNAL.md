
# Journal

## Iteration 0 — bootstrap

The scan indexed JAX-RS resources, the Faces servlet, JMS consumers, the upload schedule, and the WebSocket endpoint.
The scan did not edit application source.
SampleDataGenerator is a startup demo loader.
The loader is not a product seam.
The loader is recorded here and is not a slice.

## Iteration 1 — cargo-booking

Seed: book a new cargo.
New surfaces: ui-booking-flow, ui-cargo-admin, ui-registration-bean.
New behaviours: cargo-booking-001, cargo-booking-002, cargo-booking-003.
The wired writer is BookingBackingBean in the booking flow.
Registration.java and CargoAdmin.register have no Facelet reference in this pass.
The slice stays unbound.

## Iteration 2 — graph-shortest-path

Seed: shortest path HTTP query.
New surface: rest-graph-shortest-path.
New behaviour: graph-shortest-path-001.
The pathfinder returns random candidates.
The deadline query parameter is unused in the method body.

## Iteration 3 — itinerary-assignment

Seed: request routes and assign an itinerary.
New surface: ui-itinerary-selection.
New behaviours: itinerary-assignment-001, itinerary-assignment-002.
This slice calls the graph service through ExternalRoutingService.
The async chain is not collapsed into the graph slice.

## Iteration 4 — destination-change

Seed: change the destination of a booked cargo.
New surface: ui-change-destination.
New behaviour: destination-change-001.
CargoAdmin.changeDestination is a second caller of the same facade method.

## Iteration 5 — cargo-monitor

Seed: list cargo status.
New surfaces: rest-cargo-monitor, ui-admin-cargo-lists.
New behaviours: cargo-monitor-001, cargo-monitor-002.
The REST list and the admin filters are different projections.

## Iteration 6 — public-track

Seed: track one cargo by tracking id.
New surface: ui-public-track.
New behaviour: public-track-001.
No REST method loads one cargo with handling history.

## Iteration 7 — handling-report

Seed: submit a handling registration attempt.
New surfaces: rest-handling-reports, ui-mobile-event-logger.
New behaviours: handling-report-001, handling-report-002.
Both callers enqueue an attempt.
Neither caller stores the handling event.

## Iteration 8 — handling-file-ingest

Seed: scheduled handling-event file intake.
New surface: scheduler-upload-directory.
New behaviour: handling-file-ingest-001.
The schedule comment says fifteen minutes.
The annotation runs every two minutes.

## Iteration 9 — handling-registration

Seed: consume a handling registration attempt.
New surface: jms-handling-registration-attempt.
New behaviour: handling-registration-001.
This consumer stores the event and publishes CargoHandledQueue.

## Iteration 10 — cargo-inspection

Seed: inspect a cargo after handling.
New surfaces: jms-cargo-handled, jms-misdirected-cargo, jms-delivered-cargo, ws-realtime-tracking, jms-rejected-registration.
New behaviours: cargo-inspection-001, cargo-inspection-002, cargo-inspection-003, cargo-inspection-004, cargo-inspection-005.
No Java sender for RejectedRegistrationAttemptsQueue was found in this repository.

## Stop

Ten slices have Phase A packs.
The next entrypoint clusters are domain rules, the pathfinder DAO, the WebLogic twin, persistence, and static pages.
Those items stay in unscanned_hints.
A further product slice was not opened.
Phase B did not run.

## Overnight conductor run

All times are UTC on 2026-10-09.

| Time | Step | Decision or result | Paths |
| --- | --- | --- | --- |
| 14:24 | Charter | Operator asked for a full unattended run with two apps side by side. MODE=PROVISIONAL_OVERNIGHT. Two slices, one feature each. | overnight/CHARTER.md |
| 14:25 | Legacy runtime | No JDK 8/11, Maven, or Java EE server on the VM. Downloaded Temurin JDK 11, Maven 3.9.9, Payara 5.2022.5 to /opt/tools. | - |
| 14:26 | Legacy build | Two build blocks. (1) http repositories: mapped to https mirrors in ~/.m2/settings.xml. (2) ui-lightness 1.0.10 theme jar is not on any public repository: built a local stand-in from jQuery UI 1.10.4. No legacy source change. | ~/.m2 |
| 14:29 | Legacy deploy | Deploy failed: Payara 5 has no embedded Derby driver. Added derby-10.14.2.0.jar to domain lib. Deploy passed. GET /rest/cargo returns four cargos. | - |
| 14:40 | Legacy probe | Track page post works by script. Unknown id shows no result and no message. Next-activity text never renders. Both are pinned, not fixed. | - |
| 15:05 | Discovery bind | Provisional accept cargo-monitor-001 and public-track-001. Provisional defer cargo-monitor-002. | discovery/*/OVERNIGHT_BIND.md |
| 15:05 | Discovery Phase B | Two behaviour cards written. | discovery/*/features/ |
| 15:10 | Test generation | 7 cases. Matrix approved_provisional. | testgen/ |
| 15:15 | Test execution | RECORD on legacy. 7 of 7 REPLAY_GREEN. Separate REPLAY at 15:20: 7 of 7 green. Goldens overnight_pending_human_approve. No waiver needed. | tests/characterization/ |
| 15:25 | Architecture | Pack cargotracker-springboot3-strangler@1. Validates against architecture-pack.schema.json. BOUND as overnight-provisional/Ash Osborne. | architecture/cargotracker-modern/ |
| 15:30 | Inventory | APP_MANIFEST updated. Validates against the factory control repository schema. COVERAGE regenerated. | inventory/cargotracker/ |

### Blockers and gaps

- migration-factory/schemas/app-manifest.schema.json in this repository is older than the conversion prompt. It rejects `impl_in_modern`, and the Phase A manifest fails it too. The control repository schema accepts both. The pack denies edits to migration-factory/**, so the run did not update the vendored copy.
- migration-factory/inventory/gen_coverage.py is absent from this repository. The run used the control repository copy.

### Conversion and Verification

| Time | Step | Decision or result | Paths |
| --- | --- | --- | --- |
| 15:40 | Conversion plan | Read-only plan for both features. Mid-gate is mandatory by rule; the charter lets the run continue. A human reviews the plan with the PRs. | overnight/conversion/PLAN.md |
| 15:45 | Conversion 1 | cargo-monitor-001 on Spring Boot 3. Domain core ported. 8 of 8 modern unit tests pass. Legacy REPLAY green. PR 3. | modern/ |
| 15:55 | Conversion 2 | public-track-001 on Spring MVC + Thymeleaf. 11 of 11 modern unit tests pass. Legacy REPLAY green. PR 4. | modern/ |
| 16:00 | Verification | COMPARE modern against legacy goldens, read-only. 7 of 7 MATCH. Goldens unchanged (hash check). Verdict BLOCKED: GOLDENS_PENDING_HUMAN_APPROVE. | verification/ |
| 16:02 | Negative control | Modern with TZ=America/New_York: 4 of 6 track cases MISMATCH. Harness reports drift. | - |
| 16:10 | Demo + brief | Side-by-side page. Morning brief rewritten; Phase A brief kept below it. | overnight/demo/, overnight/MORNING_BRIEF.md |
| 16:15 | Gap from recording | The side-by-side recording showed legacy keeping the previous result after an unknown id in the browser. Cause: forceSelection clears the input, the empty post fails the required check, and the view-scoped bean keeps the old cargo. New case public-track-001-C007 (two posts on the same page). Harness RECORD now refuses to rewrite existing goldens. | testgen/public-track/, tests/characterization/public-track/ |
| 16:17 | RECORD C007 | Legacy RECORD of C007 only. REPLAY of all cases: 8 of 8 green. | tests/characterization/ |
| 16:20 | COMPARE again | public-track 6 of 7 MATCH. C007 FAIL: modern shows no result after an empty post. Verdict FAIL, human decision. Run 1600Z for public-track is superseded. | verification/public-track/2026-10-09T1620Z-modern-compare/ |
