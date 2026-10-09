
# SME brief — cargo-monitor

## What the scan found

The REST resource lists every cargo with delivery flags.
The admin bean lists every cargo and filters the DTO in memory.
The two payloads are not the same.

## Boundaries

In this slice: list cargo.
Out of this slice: track one id, and all writes.

## Recommended bind

Accept cargo-monitor-001.
Defer cargo-monitor-002 until the operator decides that admin list screens are in the pathfinder.
The defer is a recommendation.
The MANIFEST still says candidate.

## Open questions

Does getAllCargo fail when the last known location is null?
Which DTO fields set isRouted and isClaimed?

## Stop

Phase A stops here.
Do not deepen these rows until a human bind.
