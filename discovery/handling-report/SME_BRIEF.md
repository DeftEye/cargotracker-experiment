
# SME brief — handling-report

## What the scan found

REST and the mobile flow both publish a handling attempt.
Neither path stores the event.
The REST send expires after 1000 milliseconds.
The mobile form limits the tracking id list to routed and unclaimed cargo.

## Boundaries

In this slice: accept an attempt and publish it.
Out of this slice: file ingest, the storing consumer, and inspection.

## Recommended bind

Accept handling-report-001.
Accept handling-report-002.
Keep them split.
The validation rules differ.
The async store stays in handling-registration.

## Open questions

Does Bean Validation reject a bad event type before Enum.valueOf?
Is 1000 milliseconds of time-to-live long enough for the consumer?
Which event types does the mobile form offer?

## Stop

Phase A stops here.
Do not deepen these rows until a human bind.
