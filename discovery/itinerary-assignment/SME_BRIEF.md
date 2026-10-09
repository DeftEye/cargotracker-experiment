
# SME brief — itinerary-assignment

## What the scan found

The itinerary screen loads candidates and writes one choice.
The booking service calls ExternalRoutingService.
That client calls the graph URL.
A missing cargo yields an empty candidate list.
Assign does not show a guard for a missing cargo.

## Boundaries

In this slice: request routes and assign a route.
Out of this slice: the pathfinder HTTP implementation.

## Recommended bind

Accept itinerary-assignment-001.
Accept itinerary-assignment-002.
Keep the two rows split.
One row is a read.
One row is a write.
Do not fold in graph-shortest-path.

## Open questions

Does assign throw when the tracking id is unknown?
Which itineraries fail isSatisfiedBy?
Is the admin assign path still live?

## Stop

Phase A stops here.
Do not deepen these rows until a human bind.
