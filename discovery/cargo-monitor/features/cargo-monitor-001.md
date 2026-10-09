# cargo-monitor-001 - List all cargo as a JSON array

Status: documented (OVERNIGHT_PROVISIONAL bind)
Confidence: observed-in-code
Slice: cargo-monitor

## Boundary

GET /cargo-tracker/rest/cargo
Produces application/json.
No input. No authentication.

Code: src/main/java/net/java/cargotracker/interfaces/booking/rest/CargoMonitoringService.java:26-52

## Observed behaviour

1. The service reads every cargo from CargoRepository.findAll (JPA named query Cargo.findAll).
2. For each cargo it writes one JSON object with these keys, in this order:
   - trackingId: the tracking id string.
   - routingStatus: the stored Delivery routing status name (ROUTED, NOT_ROUTED, MISROUTED).
   - misdirected: the stored Delivery misdirected flag.
   - transportStatus: the stored Delivery transport status name (NOT_RECEIVED, IN_PORT, ONBOARD_CARRIER, CLAIMED, UNKNOWN).
   - atDestination: the stored Delivery unloaded-at-destination flag.
   - origin: the UN/LOCODE of the cargo origin.
   - lastKnownLocation: the UN/LOCODE of the last known location. If the code is XXXXX, the value is the string "Unknown".
3. The array order is the order that the repository returns. With the sample data, that order is ABC123, JKL567, DEF789, MNO456.

## Delivery rules that feed the output

The service reads a stored snapshot. The snapshot comes from Delivery.derivedFrom (Delivery.java:112) when the sample loader runs.

- Routing status: NOT_ROUTED if there is no itinerary. ROUTED if the route specification is satisfied by the itinerary. Else MISROUTED (Delivery.java:315).
- Satisfied means: same origin, same final destination, and the arrival deadline is after the final leg unload time (RouteSpecification.java:72).
- Transport status comes from the type of the most recent handling event: LOAD gives ONBOARD_CARRIER; UNLOAD, RECEIVE, and CUSTOMS give IN_PORT; CLAIM gives CLAIMED; no event gives NOT_RECEIVED (Delivery.java:209).
- Misdirected is true when the last event is not expected by the itinerary (Delivery.java:244, Itinerary.java:56).
- atDestination is true only when the last event is UNLOAD at the route destination (Delivery.java:328).
- Last known location is the location of the last event, or UNKNOWN (Delivery.java:228, Delivery.java:131).

## Observed sample output (legacy, 2026-10-09)

| trackingId | routingStatus | misdirected | transportStatus | atDestination | origin | lastKnownLocation |
| --- | --- | --- | --- | --- | --- | --- |
| ABC123 | ROUTED | false | IN_PORT | false | CNHKG | USNYC |
| JKL567 | ROUTED | true | ONBOARD_CARRIER | false | CNHGH | USNYC |
| DEF789 | NOT_ROUTED | false | NOT_RECEIVED | false | CNHKG | Unknown |
| MNO456 | MISROUTED | false | CLAIMED | false | USNYC | USDAL |

MNO456 is MISROUTED because its arrival deadline (2016-03-27) is before its final leg unload (2016-10-25). This is sample data as written. Do not correct it.

## Data dependency

SampleDataGenerator.loadSampleData (SampleDataGenerator.java:46) loads locations, voyages, four cargos, itineraries, and handling events at startup.
The handling events use the HandlingEventFactory. Delivery is derived after the events are stored.

## Not in this card

- Handling event registration (slice handling-registration).
- The admin list screens (cargo-monitor-002, deferred).
