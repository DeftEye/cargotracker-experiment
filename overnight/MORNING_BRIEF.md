# Morning brief

PHASE A ONLY - UNBOUND CANDIDATES

ESTATE_SCAN_INCOMPLETE

completeness: incomplete
A human residual gate is required.

This run did not bind, PACK, RECORD, or Convert.

## Counts

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

## Top candidates

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

## Inventory delta

APP_MANIFEST was created for cargotracker.
10 seeds are scanned.
18 surfaces are candidate.
19 behaviours are candidate.
COVERAGE.md was regenerated.
No behaviour is accepted, documented, converted, or verified.
legacy_green is 0.
parity_green is 0.

## Remaining hints

- src/main/java/net/java/cargotracker/domain/model/cargo/Delivery.java
- src/main/java/net/java/pathfinder/internal
- src/weblogic
- src/main/resources/META-INF/persistence.xml
- src/main/webapp/public/about.xhtml

Allowlist seeds scanned: not applicable.
ALLOWLIST_PATHS is empty because this repository is a synthetic demo.

## Recommended bind order

1. handling-report-001, handling-registration-001, and cargo-inspection-001. This is the async handling chain. Bind the three slices separately.
2. cargo-booking-001. Defer cargo-booking-002 and cargo-booking-003 until a human confirms those beans are live.
3. graph-shortest-path-001, then itinerary-assignment-001 and itinerary-assignment-002.
4. cargo-monitor-001 and public-track-001.
5. destination-change-001 and handling-file-ingest-001.
6. Leave the four defer recommendations until the questions in the SME briefs are answered.

## Blind spots

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

## Close

Of the seeded entry points scanned in this run, these candidate slices were proposed.
The APP_MANIFEST was updated.
Nothing was bound.

Bind which slice IDs today?
