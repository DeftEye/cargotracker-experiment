
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

## Law

No feature status is accepted, documented, rejected, or deferred in a MANIFEST.
Recommendations live in SME briefs and in the morning brief.
completeness stays incomplete.
