
# Slice inputs (operator-scoped)

SLICE_ID: public-track
SLICE_SEED: track one cargo by tracking id
SEED_ENTRYPOINTS:
  - Track.onTrackById
  - src/main/webapp/public/track.xhtml
OUT_OF_SCOPE_HINTS:
  - the JSON list of all cargo
  - the realtime WebSocket
  - booking writes

Attach: migration-factory/docs/FIELD-GUIDE.md
Prompt: migration-factory/prompts/discovery-agent-v0.2.md
Schema: migration-factory/schemas/discovery-manifest.schema.md
Mode: Plan Mode / Phase A map only — STOP for human bind
