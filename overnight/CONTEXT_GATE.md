
# Context gate

This file records the estate discovery start state.
The run is Phase A only.
The run does not bind candidates.

## Identity

APP_ID: cargotracker
REPO: https://github.com/defteye/cargotracker-experiment
HEAD: bf7ff9970d2869353f9245b561147ddc0d05629b
BRANCH: cursor/cargotracker-discovery-phase-a-4d3b
FACTORY_ROOT: migration-factory
OPERATOR CHARTER NAME: Ash Osborne
COMMIT_AS: estate-discovery-loop

## Hard gate

The Field Guide is present at migration-factory/docs/FIELD-GUIDE.md.
The operator runbook is present.
The Discovery agent prompt v0.2 is present.
The slice-scoping skill is present.
The APP_MANIFEST schema is present.
The discovery MANIFEST schema is present.
The run continues.

## Charter in force

PHASE_B: false
AUTO_BIND: false
AUTO_ACCEPT: false
ALLOW_CONVERSION: false
ALLOWLIST_PATHS: empty
WRITE_SCOPE: discovery, inventory, overnight
MAX_NEW_CANDIDATES: 40
MAX_SLICES_PHASE_A: 12

## Allowlist

ALLOWLIST_PATHS is empty.
An empty allowlist is allowed on this synthetic demo repository.
This repository is the Eclipse Cargo Tracker sample.
A customer estate must set an allowlist before a scan.

## Scope

This run is application discovery.
OS host uplift is out of scope.

DEFERRED_RUNTIME_DISCOVERY:
- GlassFish and WebLogic process trees
- The embedded Derby database on a host
- The JMS broker on a host
- Runtime files under /tmp/uploads, /tmp/archive, and /tmp/failed

## Overnight conductor run (2026-10-09)

Prompt: migration-factory/prompts/overnight-conductor-v0.1.md
Charter: overnight/CHARTER.md
Start HEAD: 36d9c6b3e0bf973e972d7b1e47a886bc41e9a551

Files read for this run:
- migration-factory/docs/FIELD-GUIDE.md
- migration-factory/docs/OPERATOR-RUNBOOK.md
- migration-factory/prompts/discovery-agent-v0.2.md
- migration-factory/prompts/test-generation-agent-v0.1.md
- migration-factory/prompts/test-execution-agent-v0.1.md
- migration-factory/prompts/conversion-agent-v0.1.md
- migration-factory/prompts/verification-agent-v0.1.md
- migration-factory/skills/architecture-pack/SKILL.md
- migration-factory/schemas/architecture-pack.schema.md and .json
- migration-factory/schemas/testgen-traceability.schema.md
- migration-factory/schemas/testexec-results.schema.md
- migration-factory/schemas/app-manifest.schema.md and .json

Gap: migration-factory/inventory/gen_coverage.py is absent from this repository.
The run uses the same generator from the factory control repository (ashosborne/migration-factory).

Legacy runtime for RECORD:
- Payara Server 5.2022.5 on Eclipse Temurin JDK 11, port 8080, JVM time zone UTC.
- Derby 10.14.2.0 is added to the domain lib folder. Payara 5 does not ship the embedded Derby driver that GlassFish 4.1 shipped.
- org.primefaces.themes:ui-lightness:1.0.10 is not on any public repository. The build uses a local jar made from the jQuery UI 1.10.4 ui-lightness theme.
- The X-Frame-Options header is off on the Payara listener so that the side-by-side demo can frame the legacy page.
- No legacy source file changed.

## Law

No feature status is accepted, documented, rejected, or deferred in a MANIFEST.
Recommendations live in SME briefs and in the morning brief.
completeness stays incomplete.
