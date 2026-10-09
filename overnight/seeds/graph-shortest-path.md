
# Slice inputs (operator-scoped)

SLICE_ID: graph-shortest-path
SLICE_SEED: shortest path query
SEED_ENTRYPOINTS:
  - GraphTraversalService.findShortestPath
  - GET /rest/graph-traversal/shortest-path
OUT_OF_SCOPE_HINTS:
  - booking the cargo
  - assigning the itinerary
  - handling events

Attach: migration-factory/docs/FIELD-GUIDE.md
Prompt: migration-factory/prompts/discovery-agent-v0.2.md
Schema: migration-factory/schemas/discovery-manifest.schema.md
Mode: Plan Mode / Phase A map only — STOP for human bind
