
# SME brief — graph-shortest-path

## What the scan found

GET /rest/graph-traversal/shortest-path builds random transit paths.
The method does not compute a graph shortest path.
The deadline query parameter is unused.

## Boundaries

In this slice: the pathfinder HTTP method.
Out of this slice: the booking client that calls this URL.

## Recommended bind

Accept graph-shortest-path-001.
Keep it separate from itinerary assignment.
The HTTP method is a callable boundary.
The booking client is a different slice.

## Open questions

Is the random result the as-is contract to preserve?
Which component implements GraphDao?
What value does the server give GraphTraversalUrl?

## Stop

Phase A stops here.
Do not deepen this row until a human bind.
