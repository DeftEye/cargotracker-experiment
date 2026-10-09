
# Candidates — itinerary-assignment

Phase: A.
Status of every row: candidate.
This file is not a bind.

## itinerary-assignment-001

Provisional name: Request possible routes for a tracking id.

Confidence: observed-in-code.

Why it might belong: Route choice starts with a list of candidates for one cargo.

Evidence:
- Screen load: src/main/java/net/java/cargotracker/interfaces/booking/web/ItinerarySelection.java:56
- Facade: src/main/java/net/java/cargotracker/interfaces/booking/facade/internal/DefaultBookingServiceFacade.java:94
- Service: src/main/java/net/java/cargotracker/application/internal/DefaultBookingService.java:54
- External client: src/main/java/net/java/cargotracker/infrastructure/routing/ExternalRoutingService.java:59
- Second caller: src/main/java/net/java/cargotracker/interfaces/booking/web/CargoAdmin.java:116

Observed behaviour:
A missing cargo returns an empty list.
A found cargo calls the routing service with the route specification.
ExternalRoutingService calls GraphTraversalUrl with origin and destination.
The client drops paths that fail routeSpecification.isSatisfiedBy.
The deadline is not sent on this client call.

## itinerary-assignment-002

Provisional name: Assign a selected route to a cargo.

Confidence: observed-in-code.

Why it might belong: The same screen writes the chosen itinerary.

Evidence:
- Screen assign: ItinerarySelection.java:62
- Facade: DefaultBookingServiceFacade.java:63
- Service: DefaultBookingService.java:65
- Admin assign: CargoAdmin.java:120

Observed behaviour:
The screen selects a candidate by index and calls assignCargoToRoute.
The facade rebuilds an itinerary from the DTO.
The service loads the cargo, calls assignToRoute, and stores the cargo.
The service does not test for a missing cargo before assignToRoute.
ItinerarySelection redirects to show.html.
CargoAdmin also redirects to show.html.

The graph HTTP method stays in slice graph-shortest-path.
This slice is the client of that method.
