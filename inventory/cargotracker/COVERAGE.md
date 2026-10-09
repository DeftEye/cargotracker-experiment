# Coverage — cargotracker

> **Generated from APP_MANIFEST.yaml. Never hand-edit.** Characterization flags (`legacy_green`) ≠ done in modern; use Operator progress for modern implementation.

## Operator progress (modern implementation)

| State | Count | Meaning |
| --- | ---: | --- |
| none | 18 | Not started in modern |
| partial | 0 | Some modern execution; gaps in notes |
| full (converted) | 0 | Done in modern; parity may still be UNVERIFIED |
| deferred / rejected | 1 | Explicitly out |

### Done in modern (impl_in_modern=full)

- None

### Partial in modern

- None

### Remaining (impl_in_modern none/absent, not deferred/rejected)

- cargo-booking-001 — cargo-booking — legacy_green no
- cargo-booking-002 — cargo-booking — legacy_green no
- cargo-booking-003 — cargo-booking — legacy_green no
- graph-shortest-path-001 — graph-shortest-path — legacy_green no
- itinerary-assignment-001 — itinerary-assignment — legacy_green no
- itinerary-assignment-002 — itinerary-assignment — legacy_green no
- destination-change-001 — destination-change — legacy_green no
- cargo-monitor-001 — cargo-monitor — legacy_green yes
- public-track-001 — public-track — legacy_green yes
- handling-report-001 — handling-report — legacy_green no
- handling-report-002 — handling-report — legacy_green no
- handling-file-ingest-001 — handling-file-ingest — legacy_green no
- handling-registration-001 — handling-registration — legacy_green no
- cargo-inspection-001 — cargo-inspection — legacy_green no
- cargo-inspection-002 — cargo-inspection — legacy_green no
- cargo-inspection-003 — cargo-inspection — legacy_green no
- cargo-inspection-004 — cargo-inspection — legacy_green no
- cargo-inspection-005 — cargo-inspection — legacy_green no

## Inventory context

### Seeds

| State | Count |
| --- | ---: |
| scanned | 10 |
| unscanned hints | 5 |

### Surface status histogram

| Status | Count |
| --- | ---: |
| accepted | 2 |
| candidate | 15 |
| deferred | 1 |

### Behaviour status histogram

| Status | Count |
| --- | ---: |
| candidate | 16 |
| deferred | 1 |
| documented | 2 |

### Characterization flags

| Flag | Count |
| --- | ---: |
| legacy_green | 2 |
| parity_green | 0 |

### Unscanned hints

- src/main/java/net/java/cargotracker/domain/model/cargo/Delivery.java
- src/main/java/net/java/pathfinder/internal
- src/weblogic
- src/main/resources/META-INF/persistence.xml
- src/main/webapp/public/about.xhtml
