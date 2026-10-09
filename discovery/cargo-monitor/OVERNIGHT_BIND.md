# Overnight bind - cargo-monitor

OVERNIGHT_PROVISIONAL - a human must confirm or change these decisions.
Charter: overnight/CHARTER.md

| Feature | Decision | Reason | Evidence |
| --- | --- | --- | --- |
| cargo-monitor-001 | accept | Callable REST boundary. Read only. Deterministic sample data. | CargoMonitoringService.java:26 |
| cargo-monitor-002 | defer | Admin JSF screen. The batch holds one feature per slice. | ListCargo.java:40 |

Open question from Phase A, now answered from code:
- "Does a null last known location throw?" No. Delivery.getLastKnownLocation returns Location.UNKNOWN (XXXXX) when the stored value is null (Delivery.java:131). The service maps XXXXX to "Unknown".

Morning action: confirm both rows, or edit discovery/cargo-monitor/MANIFEST.yaml and remove `bind_source: overnight_provisional`.
