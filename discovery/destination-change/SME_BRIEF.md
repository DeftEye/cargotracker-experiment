
# SME brief — destination-change

## What the scan found

One application method changes the destination.
Two screens call it.
The arrival deadline is copied from the old specification.

## Boundaries

In this slice: replace the destination on an existing cargo.
Out of this slice: create cargo, assign a route, and handling.

## Recommended bind

Accept destination-change-001 as one row.
The screen difference is a locator detail.
Split the row later if the dialog and the admin page must have separate tests.

## Open questions

Does a destination change clear the current itinerary?
What happens when the location code is unknown?

## Stop

Phase A stops here.
Do not deepen this row until a human bind.
