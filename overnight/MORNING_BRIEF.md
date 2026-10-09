# Morning brief - overnight conductor run (2026-10-09)

OVERNIGHT_PROVISIONAL - binds, matrix, goldens, and pack BIND are provisional. Nothing here is a production claim.

## Result

Two features moved from Java EE 7 to Spring Boot 3. The modern app matches 7 of 8 pinned cases. One case fails.
PARITY=GREEN is not claimed for either feature.

| Feature | Slice | Legacy | Modern | Legacy RECORD/REPLAY | COMPARE | Parity |
| --- | --- | --- | --- | --- | --- | --- |
| cargo-monitor-001 | cargo-monitor | JAX-RS GET /rest/cargo | Spring @RestController | 1/1 REPLAY_GREEN | 1/1 MATCH | BLOCKED (goldens pending approval) |
| public-track-001 | public-track | JSF public/track.xhtml | Spring MVC + Thymeleaf | 7/7 REPLAY_GREEN | 6/7 MATCH | FAIL (C007) |

The failing case (public-track-001-C007): in legacy, an unknown id in the browser is cleared by the autocomplete, the empty post fails validation, and the page keeps the previous cargo result with a red input. Modern shows no result. The first COMPARE run missed this because each case used a new page. The side-by-side screen recording showed it, and C007 now pins it on legacy.

Decision for you: copy this legacy behaviour in modern (Conversion follow-up), or accept the difference and write a waiver for C007.

Negative control: modern in time zone America/New_York gives 4 of 6 track mismatches. The harness does report drift.

## Pull requests (stacked, review in this order)

1. Discovery Phase A: https://github.com/DeftEye/cargotracker-experiment/pull/1
2. Bind, cards, test generation, goldens, pack: https://github.com/DeftEye/cargotracker-experiment/pull/2
3. Conversion cargo-monitor-001: https://github.com/DeftEye/cargotracker-experiment/pull/3
4. Conversion public-track-001: https://github.com/DeftEye/cargotracker-experiment/pull/4
5. Verification, side-by-side demo, this brief: branch cursor/cargotracker-verification-4d3b

## What is provisional

| Item | Where | Human click |
| --- | --- | --- |
| Discovery bind | discovery/cargo-monitor/OVERNIGHT_BIND.md, discovery/public-track/OVERNIGHT_BIND.md | Confirm the rows. Remove `bind_source: overnight_provisional` from both MANIFESTs. |
| Test matrix | testgen/*/TRACEABILITY.yaml | Change `approved_provisional` to `approved`. |
| Goldens | tests/characterization/*/TRACEABILITY.yaml | Read the 7 `*.approved.json` files. Change `golden_status` to approved. |
| Pack BIND | architecture/cargotracker-modern/PACK.yaml | Re-BIND under your name. Remove the banner. |
| C007 decision | verification/public-track/2026-10-09T1620Z-modern-compare/PARITY.yaml | Choose: copy the legacy behaviour, or waive C007. |
| Parity | verification/cargo-monitor/2026-10-09T1600Z-modern-compare/PARITY.yaml, verification/public-track/2026-10-09T1620Z-modern-compare/PARITY.yaml | After the clicks above, re-run COMPARE. Only then may Verification set GREEN. |

## See it

The side-by-side page frames both apps and shows the COMPARE table: overnight/demo/side-by-side.html. Start-up steps: overnight/demo/README.md.

## Why these two slices and not the whole application

- The conductor prompt refuses whole-repo Conversion. It allows one feature per slice.
- The conductor prompt recommends tracking read paths first. The Phase A brief put them fourth because it ranked by business weight. The overnight run needs callable read-only boundaries that RECORD can pin without a human.
- The ported domain core (Delivery and its rules) is shared. Later slices can reuse it.

## Top risks

1. CDDL: modern/ ports javaee/cargotracker code. Keep the licence notice before any release.
2. Legacy runtime: GlassFish 4.1 is gone. RECORD used Payara 5 plus a Derby jar and a stand-in theme jar. Goldens are only as true as that runtime.
3. Time zone: date text follows the JVM time zone. Both apps must run with UTC for COMPARE.
4. Data: modern seeds in memory. The first write slice needs a new pack version with a data strategy.
5. Vendored factory pack: migration-factory/schemas/app-manifest.schema.json in this repository is stale and rejects `impl_in_modern`.
6. Unpinned deltas: modern header text, no map panel, no autocomplete list.

## Next wave

16 behaviours remain (see inventory/cargotracker/COVERAGE.md). Suggested next: handling-report-001 (POST /rest/handling/reports). It is the first write path, so it needs the data strategy decision first.

---

# Phase A brief (earlier run)

### Phase A brief content

PHASE A ONLY - UNBOUND CANDIDATES

ESTATE_SCAN_INCOMPLETE

completeness: incomplete
A human residual gate is required.

This run did not bind, PACK, RECORD, or Convert.

### Counts

| Item | Count |
| --- | ---: |
| Seeds scanned | 10 |
| New candidate features | 19 |
| Defer recommendations in prose | 4 |
| Errors | 0 |

Defer recommendations, still candidate in the MANIFEST:
- cargo-booking-002
- cargo-booking-003
- cargo-monitor-002
- cargo-inspection-002
- cargo-inspection-003
- cargo-inspection-005

### Top candidates

| Id | Slice | Boundary hint | Confidence | Evidence |
| --- | --- | --- | --- | --- |
| cargo-booking-001 | cargo-booking | BookingBackingBean.register | observed-in-code | src/main/java/net/java/cargotracker/domain/model/cargo/BookingBackingBean.java:129 |
| graph-shortest-path-001 | graph-shortest-path | GET /rest/graph-traversal/shortest-path | observed-in-code | src/main/java/net/java/pathfinder/api/GraphTraversalService.java:24 |
| itinerary-assignment-001 | itinerary-assignment | ItinerarySelection.load | observed-in-code | src/main/java/net/java/cargotracker/interfaces/booking/web/ItinerarySelection.java:56 |
| itinerary-assignment-002 | itinerary-assignment | ItinerarySelection.assignItinerary | observed-in-code | src/main/java/net/java/cargotracker/interfaces/booking/web/ItinerarySelection.java:62 |
| destination-change-001 | destination-change | ChangeDestination.changeDestination | observed-in-code | src/main/java/net/java/cargotracker/interfaces/booking/web/ChangeDestination.java:80 |
| cargo-monitor-001 | cargo-monitor | GET /rest/cargo | observed-in-code | src/main/java/net/java/cargotracker/interfaces/booking/rest/CargoMonitoringService.java:26 |
| public-track-001 | public-track | Track.onTrackById | observed-in-code | src/main/java/net/java/cargotracker/interfaces/tracking/web/Track.java:72 |
| handling-report-001 | handling-report | POST /rest/handling/reports | observed-in-code | src/main/java/net/java/cargotracker/interfaces/handling/rest/HandlingReportService.java:38 |
| handling-file-ingest-001 | handling-file-ingest | UploadDirectoryScanner | observed-in-code | src/main/java/net/java/cargotracker/interfaces/handling/file/UploadDirectoryScanner.java:18 |
| handling-registration-001 | handling-registration | HandlingEventRegistrationAttemptConsumer | observed-in-code | src/main/java/net/java/cargotracker/infrastructure/messaging/jms/HandlingEventRegistrationAttemptConsumer.java:31 |
| cargo-inspection-001 | cargo-inspection | CargoHandledConsumer | observed-in-code | src/main/java/net/java/cargotracker/infrastructure/messaging/jms/CargoHandledConsumer.java:35 |

### Inventory delta

APP_MANIFEST was created for cargotracker.
10 seeds are scanned.
18 surfaces are candidate.
19 behaviours are candidate.
COVERAGE.md was regenerated.
No behaviour is accepted, documented, converted, or verified.
legacy_green is 0.
parity_green is 0.

### Remaining hints

- src/main/java/net/java/cargotracker/domain/model/cargo/Delivery.java
- src/main/java/net/java/pathfinder/internal
- src/weblogic
- src/main/resources/META-INF/persistence.xml
- src/main/webapp/public/about.xhtml

Allowlist seeds scanned: not applicable.
ALLOWLIST_PATHS is empty because this repository is a synthetic demo.

### Recommended bind order

1. handling-report-001, handling-registration-001, and cargo-inspection-001. This is the async handling chain. Bind the three slices separately.
2. cargo-booking-001. Defer cargo-booking-002 and cargo-booking-003 until a human confirms those beans are live.
3. graph-shortest-path-001, then itinerary-assignment-001 and itinerary-assignment-002.
4. cargo-monitor-001 and public-track-001.
5. destination-change-001 and handling-file-ingest-001.
6. Leave the four defer recommendations until the questions in the SME briefs are answered.

### Blind spots

- Delivery derivation rules are called and not seeded.
- GraphDao is behind the pathfinder resource and not seeded.
- RejectedRegistrationAttemptsQueue has no sender in this repository.
- The upload schedule comment and the annotation do not match.
- GraphTraversalService ignores the deadline parameter.
- web.xml declares GraphTraversalUrl as a server env-entry. The live value is runtime configuration.
- SampleDataGenerator loads demo data at startup. It is not a product slice.
- Registration.java and CargoAdmin.register have no Facelet reference in src/main/webapp.
- admin/track.xhtml calls completeTracking and onPointSelect. Track.java does not declare those methods.
- The wired booking flow returns the dashboard and does not display the new tracking id.
- Host, Derby, and JMS broker discovery is deferred.

### Close

Of the seeded entry points scanned in this run, these candidate slices were proposed.
The APP_MANIFEST was updated.
Nothing was bound.

Bind which slice IDs today?
