
# SME brief — handling-registration

## What the scan found

The registration queue is the store path for a handling event.
Submitters only publish the attempt.
This consumer stores the event and emits a handled message.
Cargo delivery progress is not updated in this method.

## Boundaries

In this slice: consume an attempt and store the event.
Out of this slice: the three submitters, and inspection of the handled message.

## Recommended bind

Accept handling-registration-001.
Keep the async boundary.
Do not merge this row with the REST submit.

## Open questions

Which factory checks reject an attempt?
Is there a dead-letter consumer in this repository?

## Stop

Phase A stops here.
Do not deepen this row until a human bind.
