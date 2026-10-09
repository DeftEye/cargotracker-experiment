
# Slice inputs (operator-scoped)

SLICE_ID: cargo-booking
SLICE_SEED: book a new cargo
SEED_ENTRYPOINTS:
  - BookingBackingBean.register
  - JSF flow booking
  - Registration.register
  - CargoAdmin.register
  - BookingServiceFacade.bookNewCargo
  - DefaultBookingService.bookNewCargo
OUT_OF_SCOPE_HINTS:
  - route search
  - itinerary assignment
  - destination change
  - tracking
  - handling events

Attach: migration-factory/docs/FIELD-GUIDE.md
Prompt: migration-factory/prompts/discovery-agent-v0.2.md
Schema: migration-factory/schemas/discovery-manifest.schema.md
Mode: Plan Mode / Phase A map only — STOP for human bind
