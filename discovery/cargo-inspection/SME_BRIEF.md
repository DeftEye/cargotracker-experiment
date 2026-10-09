
# SME brief — cargo-inspection

## What the scan found

A handled message derives delivery progress and stores the cargo.
Misdirected and delivered messages are log-only.
The WebSocket pushes a small JSON object after inspection.
The rejected-attempt queue has a consumer and no sender in this repository.

## Boundaries

In this slice: react to a handled cargo.
Out of this slice: storing the handling event, booking writes, and public track by id.

## Recommended bind

Accept cargo-inspection-001.
Accept cargo-inspection-004 if realtime push is in the pathfinder.
Defer cargo-inspection-002 and cargo-inspection-003.
Those consumers only log.
Defer cargo-inspection-005 until a sender is found or a human marks the queue as dead.
These defer lines are recommendations.
The MANIFEST status of each row stays candidate.

## Open questions

Which rules does deriveDeliveryProgress use?
Can one inspect publish both misdirected and delivered?
Who should send RejectedRegistrationAttemptsQueue?

## Stop

Phase A stops here.
Do not deepen these rows until a human bind.
