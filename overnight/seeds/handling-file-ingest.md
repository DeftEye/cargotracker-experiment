
# Slice inputs (operator-scoped)

SLICE_ID: handling-file-ingest
SLICE_SEED: scheduled handling-event file intake
SEED_ENTRYPOINTS:
  - UploadDirectoryScanner.processFiles
  - EventFilesProcessorJob
  - EventItemReader
  - EventItemWriter
OUT_OF_SCOPE_HINTS:
  - the REST handling report
  - the mobile event logger
  - the consumer that stores the event

Attach: migration-factory/docs/FIELD-GUIDE.md
Prompt: migration-factory/prompts/discovery-agent-v0.2.md
Schema: migration-factory/schemas/discovery-manifest.schema.md
Mode: Plan Mode / Phase A map only — STOP for human bind
