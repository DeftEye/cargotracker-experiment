
# Slice inputs (operator-scoped)

SLICE_ID: handling-registration
SLICE_SEED: store a handling event from the registration queue
SEED_ENTRYPOINTS:
  - HandlingEventRegistrationAttemptConsumer.onMessage
  - DefaultHandlingEventService.registerHandlingEvent
OUT_OF_SCOPE_HINTS:
  - REST and mobile submit
  - file ingest
  - cargo inspection after the handled message

Attach: migration-factory/docs/FIELD-GUIDE.md
Prompt: migration-factory/prompts/discovery-agent-v0.2.md
Schema: migration-factory/schemas/discovery-manifest.schema.md
Mode: Plan Mode / Phase A map only — STOP for human bind
