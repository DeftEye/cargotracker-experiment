# SME brief — cargo-booking

## What the scan found

The wired booking path is the JSF flow booking.
BookingBackingBean.register stores a cargo and returns the admin dashboard.
The new tracking id is not kept on the bean.
The book button requires a journey of at least one day.
Registration.java and CargoAdmin.register also call bookNewCargo.
This pass found no Facelet that references those two beans.

## Boundaries

In this slice: create a cargo and return a tracking id.
Out of this slice: routes, assignment, destination change, tracking, and handling.

## Recommended bind

Accept cargo-booking-001.
Defer cargo-booking-002 and cargo-booking-003 until a human confirms the beans are live.
The defer lines are recommendations.
The MANIFEST status of each row stays candidate.

## Open questions

Which view starts showing the new tracking id after booking?
Is CargoAdmin a leftover bean?
Is Registration a leftover bean?

## Stop

Phase A stops here.
Do not deepen these rows until a human bind.
