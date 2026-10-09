
# Slice inputs (operator-scoped)

SLICE_ID: itinerary-assignment
SLICE_SEED: request routes and assign an itinerary
SEED_ENTRYPOINTS:
  - ItinerarySelection.load
  - ItinerarySelection.assignItinerary
  - BookingServiceFacade.requestPossibleRoutesForCargo
  - BookingServiceFacade.assignCargoToRoute
  - ExternalRoutingService.fetchRoutesForSpecification
OUT_OF_SCOPE_HINTS:
  - the graph HTTP implementation
  - booking a new cargo
  - destination change
  - handling events

Attach: migration-factory/docs/FIELD-GUIDE.md
Prompt: migration-factory/prompts/discovery-agent-v0.2.md
Schema: migration-factory/schemas/discovery-manifest.schema.md
Mode: Plan Mode / Phase A map only — STOP for human bind
