
# Slice inputs (operator-scoped)

SLICE_ID: destination-change
SLICE_SEED: change the destination of a booked cargo
SEED_ENTRYPOINTS:
  - ChangeDestination.changeDestination
  - CargoAdmin.changeDestination
  - BookingServiceFacade.changeDestination
OUT_OF_SCOPE_HINTS:
  - booking a new cargo
  - itinerary assignment
  - handling events

Attach: migration-factory/docs/FIELD-GUIDE.md
Prompt: migration-factory/prompts/discovery-agent-v0.2.md
Schema: migration-factory/schemas/discovery-manifest.schema.md
Mode: Plan Mode / Phase A map only — STOP for human bind
