# Candidates — cargo-booking

Phase: A.
Status of every row: candidate.
This file is not a bind.

## cargo-booking-001

Provisional name: Book a new cargo in the booking flow.

Confidence: observed-in-code.

Why it might belong: The admin header Book link starts the booking flow. The flow calls bookNewCargo.

Evidence:
- Header link: src/main/webapp/WEB-INF/templates/common/admin.xhtml:31
- Flow id: src/main/webapp/booking/booking-flow.xml:7
- Bean: src/main/java/net/java/cargotracker/domain/model/cargo/BookingBackingBean.java:22
- Register: BookingBackingBean.java:129
- Deadline gate: BookingBackingBean.java:161
- Book button: src/main/webapp/booking/booking-date.xhtml:48
- Facade store: src/main/java/net/java/cargotracker/application/internal/DefaultBookingService.java:35

Observed behaviour:
The flow has three views: origin, destination, and deadline.
getLocations removes the opposite selected code from the menu.
deadlineUpdated sets bookable only when the duration is at least one day.
The Book button stays disabled until bookable is true.
register rejects an equal origin and destination.
register calls bookNewCargo with a Date deadline.
register returns /admin/dashboard.xhtml.
register does not store the new tracking id on the bean.
A thrown exception becomes RuntimeException with the text Error parsing date.
The comment in register says the method no longer parses a date.

## cargo-booking-002

Provisional name: Book a new cargo from the admin bean.

Confidence: needs-SME.

Why it might belong: CargoAdmin.register calls the same facade method.

Evidence:
- CargoAdmin.register: src/main/java/net/java/cargotracker/interfaces/booking/web/CargoAdmin.java:106
- Facade method: src/main/java/net/java/cargotracker/interfaces/booking/facade/internal/DefaultBookingServiceFacade.java:46

Observed behaviour:
This method calls bookNewCargo with a Date deadline.
This method does not compare origin and destination.
The return path is show.html.
This pass found no Facelet reference to cargoAdmin.

## cargo-booking-003

Provisional name: Book a new cargo from Registration.java.

Confidence: needs-SME.

Why it might belong: Registration.register also calls bookNewCargo and rejects an equal origin and destination.

Evidence:
- Registration.register: src/main/java/net/java/cargotracker/interfaces/booking/web/Registration.java:73
- Same-location guard: Registration.java:77

Observed behaviour:
The method parses the deadline with pattern yyyy-MM-dd.
An equal origin and destination adds a Faces error and returns null.
A different origin redirects to show.xhtml with the tracking id.
This pass found no Facelet reference to registration.
The wired booking pages use bookingBackingBean.
