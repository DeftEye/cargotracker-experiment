
# Candidates — destination-change

Phase: A.
Status of every row: candidate.
This file is not a bind.

## destination-change-001

Provisional name: Change the destination of a booked cargo.

Confidence: observed-in-code.

Why it might belong: Two screens call the same facade method to replace the destination.

Evidence:
- Dialog bean: src/main/java/net/java/cargotracker/interfaces/booking/web/ChangeDestination.java:80
- Admin bean: src/main/java/net/java/cargotracker/interfaces/booking/web/CargoAdmin.java:127
- Facade: src/main/java/net/java/cargotracker/interfaces/booking/facade/internal/DefaultBookingServiceFacade.java:74
- Service: src/main/java/net/java/cargotracker/application/internal/DefaultBookingService.java:75

Observed behaviour:
The service loads the cargo and the new location.
The service builds a new route specification.
The origin and the arrival deadline stay the same.
The service stores the cargo.
The dialog bean closes the dialog with DONE.
The admin bean redirects to show.html.
The service does not test for a missing cargo.
