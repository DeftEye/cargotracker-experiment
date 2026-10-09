
# SME brief — handling-file-ingest

## What the scan found

A timer starts EventFilesProcessorJob.
The job parses CSV lines into handling attempts and publishes them.
The schedule period in code is two minutes.
The comment says fifteen minutes.
Failed-directory handling is declared and not shown in the reader or the writer.

## Boundaries

In this slice: read files and publish attempts.
Out of this slice: REST submit, mobile submit, and the storing consumer.

## Recommended bind

Accept handling-file-ingest-001.
Keep it separate from the REST report.
The file contract is a different boundary.

## Open questions

Which class moves a bad file to /tmp/failed?
Does the job run when the directory is empty?

## Stop

Phase A stops here.
Do not deepen this row until a human bind.
