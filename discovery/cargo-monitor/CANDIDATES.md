
# Candidates — cargo-monitor

Phase: A.
Status of every row: candidate.
This file is not a bind.

## cargo-monitor-001

Provisional name: List all cargo as a JSON array.

Confidence: observed-in-code.

Why it might belong: GET /rest/cargo is the HTTP list of cargo status.

Evidence:
- Resource: src/main/java/net/java/cargotracker/interfaces/booking/rest/CargoMonitoringService.java:17
- Method: CargoMonitoringService.java:26
- Package registration: src/main/java/net/java/cargotracker/application/util/RestConfiguration.java:19

Observed behaviour:
The method loads every cargo.
Each object has trackingId, routingStatus, misdirected, transportStatus, atDestination, origin, and lastKnownLocation.
A last known location code of XXXXX becomes the string Unknown.
The method returns a JSON array.
The method has no query parameters.

## cargo-monitor-002

Provisional name: Filter admin cargo lists.

Confidence: observed-in-code.

Why it might belong: The admin screens split the same cargo list into routed, not routed, claimed, and routed-unclaimed lists.

Evidence:
- Bean: src/main/java/net/java/cargotracker/interfaces/booking/web/ListCargo.java:40
- Routed filter: ListCargo.java:45
- Routed and unclaimed filter: ListCargo.java:57
- Claimed filter: ListCargo.java:68
- Not routed filter: ListCargo.java:80
- Views: src/main/webapp/admin/listRouted.xhtml:7, listNotRouted.xhtml:6, listClaimed.xhtml:7

Observed behaviour:
init loads every cargo through the booking facade.
The filters use isRouted and isClaimed on the DTO.
listRouted.xhtml reads routedUnclaimedCargos, not routedCargos.
This projection is not the REST payload.

Recommendation in prose: defer this row if the pathfinder characterises only the REST list.
The MANIFEST status stays candidate.
