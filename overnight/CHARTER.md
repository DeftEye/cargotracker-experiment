# Overnight conductor charter

Prompt: migration-factory/prompts/overnight-conductor-v0.1.md
Instruction source: operator request on 2026-10-09 to run the migration without the operator and show the old app and the new app side by side.

OVERNIGHT_PROVISIONAL - every bind, matrix approval, golden approval, and pack BIND in this run is provisional. A human must confirm each one before any production claim.

```yaml
OPERATOR: Ash Osborne
APP_ID: cargotracker
REPO_ROOT: .
FACTORY_ROOT: migration-factory
SLICE_IDS:
  - cargo-monitor
  - public-track
MODE: PROVISIONAL_OVERNIGHT
ALLOW_PROVISIONAL_BIND: true
ALLOW_PROVISIONAL_MATRIX: true
ALLOW_WAIVE_RECORD: true
ALLOW_PROVISIONAL_PACK_BIND: true
ALLOW_CONVERSION: true
FIRST_CONVERSION_BATCH_MAX: 1   # per slice
ALLOW_VERIFICATION_COMPARE: true
COMMIT_AS: overnight-conductor
```

## Why two slices and not the whole application

The conductor prompt refuses whole-repo Conversion.
The run takes two slices from the Phase A packs.
Each slice converts one feature.

| Slice | Feature | Boundary |
| --- | --- | --- |
| cargo-monitor | cargo-monitor-001 | GET /rest/cargo |
| public-track | public-track-001 | public/track.xhtml |

Reasons for this choice:
- The conductor prompt recommends tracking read paths first.
- Both features read sample data only. They do not need JMS, batch, or the routing service.
- Both features have a callable boundary. The harness can RECORD them on a running legacy server.
- Both features use the same delivery derivation rules in Delivery.java. One port of the domain core serves both features.

## Changes to the charter defaults

| Knob | Default | This run | Reason |
| --- | --- | --- | --- |
| SLICE_ID | cargo-tracking | cargo-monitor and public-track | The Phase A run already split tracking into these two slices. |
| FIRST_CONVERSION_BATCH_MAX | 1 | 1 per slice | The operator asked for a full run. The prompt still caps each slice at one feature. |
| ALLOW_VERIFICATION_COMPARE | false | true | The legacy server runs, so real goldens exist. COMPARE may report a result. It may not set PARITY=GREEN while goldens wait for human approval. |

## Still forbidden

- PARITY=GREEN.
- A RECORD from modern code.
- Edits outside the pack edit surface.
- Behaviour that the legacy tree does not show.
