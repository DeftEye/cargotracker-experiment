
# Candidates — graph-shortest-path

Phase: A.
Status of every row: candidate.
This file is not a bind.

## graph-shortest-path-001

Provisional name: Return route candidates for an origin and a destination.

Confidence: observed-in-code.

Why it might belong: This method is the HTTP boundary of the pathfinder.

Evidence:
- Application path: src/main/java/net/java/cargotracker/application/util/RestConfiguration.java:14
- Resource: src/main/java/net/java/pathfinder/api/GraphTraversalService.java:15
- Method: GraphTraversalService.java:24
- Package registration: RestConfiguration.java:19

Observed behaviour:
The method requires origin and destination query parameters.
Each code must have length 5.
The deadline parameter is present and unused.
The method loads locations from GraphDao.
The method builds 3 to 5 paths.
Each path uses shuffled intermediate locations and random dates.
The method does not read the deadline.
The response is JSON, or XML with a lower quality value.

DEFERRED_RUNTIME_DISCOVERY: The live GraphTraversalUrl value is a server env-entry.
See web.xml line 37.
