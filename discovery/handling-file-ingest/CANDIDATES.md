
# Candidates — handling-file-ingest

Phase: A.
Status of every row: candidate.
This file is not a bind.

## handling-file-ingest-001

Provisional name: Scan upload files and enqueue handling attempts.

Confidence: observed-in-code.

Why it might belong: A schedule starts a batch job that turns file lines into attempts.

Evidence:
- Schedule: src/main/java/net/java/cargotracker/interfaces/handling/file/UploadDirectoryScanner.java:18
- Job: src/main/resources/META-INF/batch-jobs/EventFilesProcessorJob.xml:2
- Reader: src/main/java/net/java/cargotracker/interfaces/handling/file/EventItemReader.java:97
- Writer: src/main/java/net/java/cargotracker/interfaces/handling/file/EventItemWriter.java:39

Observed behaviour:
The timer runs every two minutes.
The comment on the annotation says fifteen minutes.
The job reads /tmp/uploads.
A line must have five comma-separated fields.
Field 1 is the completion time with pattern yyyy-MM-dd HH:mm.
Field 2 is the tracking id.
Field 3 is the voyage number, and an empty field means no voyage.
Field 4 is the UN location code.
Field 5 is the event type name.
A bad line is a skippable EventLineParseException.
The writer publishes each attempt.
The writer appends a CSV archive under /tmp/archive.
The reader deletes a fully read upload file.
The comment on the scanner says failed files move to another directory.
The job defines /tmp/failed.
This pass did not find a move into /tmp/failed.

The store of the event is slice handling-registration.
