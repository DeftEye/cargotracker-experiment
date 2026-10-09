
# Slice inputs (operator-scoped)

SLICE_ID: cargo-monitor
SLICE_SEED: list cargo status
SEED_ENTRYPOINTS:
  - CargoMonitoringService.getAllCargo
  - GET /rest/cargo
  - ListCargo
OUT_OF_SCOPE_HINTS:
  - track one cargo by id
  - booking writes
  - handling events

Attach: migration-factory/docs/FIELD-GUIDE.md
Prompt: migration-factory/prompts/discovery-agent-v0.2.md
Schema: migration-factory/schemas/discovery-manifest.schema.md
Mode: Plan Mode / Phase A map only — STOP for human bind
