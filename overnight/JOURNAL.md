
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
