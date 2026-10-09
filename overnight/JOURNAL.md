# Overnight conductor journal

All times are UTC on 2026-10-09.

| Time | Step | Decision or result | Paths |
| --- | --- | --- | --- |
| 14:24 | Charter | Operator asked for a full unattended run with two apps side by side. MODE=PROVISIONAL_OVERNIGHT. Two slices, one feature each. | overnight/CHARTER.md |
| 14:25 | Legacy runtime | No JDK 8/11, Maven, or Java EE server on the VM. Downloaded Temurin JDK 11, Maven 3.9.9, Payara 5.2022.5 to /opt/tools. | - |
| 14:26 | Legacy build | Two build blocks. (1) http repositories: mapped to https mirrors in ~/.m2/settings.xml. (2) ui-lightness 1.0.10 theme jar is not on any public repository: built a local stand-in from jQuery UI 1.10.4. No legacy source change. | ~/.m2 |
| 14:29 | Legacy deploy | Deploy failed: Payara 5 has no embedded Derby driver. Added derby-10.14.2.0.jar to domain lib. Deploy passed. GET /rest/cargo returns four cargos. | - |
| 14:40 | Legacy probe | Track page post works by script. Unknown id shows no result and no message. Next-activity text never renders. Both are pinned, not fixed. | - |
| 15:05 | Discovery bind | Provisional accept cargo-monitor-001 and public-track-001. Provisional defer cargo-monitor-002. | discovery/*/OVERNIGHT_BIND.md |
| 15:05 | Discovery Phase B | Two behaviour cards written. | discovery/*/features/ |
| 15:10 | Test generation | 7 cases. Matrix approved_provisional. | testgen/ |
| 15:15 | Test execution | RECORD on legacy. 7 of 7 REPLAY_GREEN. Separate REPLAY at 15:20: 7 of 7 green. Goldens overnight_pending_human_approve. No waiver needed. | tests/characterization/ |
| 15:25 | Architecture | Pack cargotracker-springboot3-strangler@1. Validates against architecture-pack.schema.json. BOUND as overnight-provisional/Ash Osborne. | architecture/cargotracker-modern/ |
| 15:30 | Inventory | APP_MANIFEST updated. Validates against the factory control repository schema. COVERAGE regenerated. | inventory/cargotracker/ |

## Blockers and gaps

- migration-factory/schemas/app-manifest.schema.json in this repository is older than the conversion prompt. It rejects `impl_in_modern`, and the Phase A manifest fails it too. The control repository schema accepts both. The pack denies edits to migration-factory/**, so the run did not update the vendored copy.
- migration-factory/inventory/gen_coverage.py is absent from this repository. The run used the control repository copy.
