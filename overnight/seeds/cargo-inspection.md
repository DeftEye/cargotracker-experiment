
# Slice inputs (operator-scoped)

SLICE_ID: cargo-inspection
SLICE_SEED: inspect cargo after a handling event
SEED_ENTRYPOINTS:
  - CargoHandledConsumer.onMessage
  - DefaultCargoInspectionService.inspectCargo
  - MisdirectedCargoConsumer
  - DeliveredCargoConsumer
  - RealtimeCargoTrackingService
  - RejectedRegistrationAttemptsConsumer
OUT_OF_SCOPE_HINTS:
  - storing the handling event
  - booking writes
  - public track by id

Attach: migration-factory/docs/FIELD-GUIDE.md
Prompt: migration-factory/prompts/discovery-agent-v0.2.md
Schema: migration-factory/schemas/discovery-manifest.schema.md
Mode: Plan Mode / Phase A map only — STOP for human bind
