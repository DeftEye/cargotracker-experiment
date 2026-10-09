# cargo-monitor-001-C001 - List all sample cargo as JSON

Kind: characterization
Evidence gate: observed-in-code
Discovery card: discovery/cargo-monitor/features/cargo-monitor-001.md
Evidence: CargoMonitoringService.java:26-52; Delivery.java:112; SampleDataGenerator.java:46

Boundary: GET /rest/cargo
Input: No input.
Fixture: legacy startup sample data (SampleDataGenerator). JVM time zone UTC.

Observables:
- http.status
- content_type (media type only)
- body (all objects, array order)
- key_order per object

Expected values: TO_BE_RECORDED. Test execution writes the golden from legacy output.
Scrub: collapse white space; decode HTML entities. No other scrub.
