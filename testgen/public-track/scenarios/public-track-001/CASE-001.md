# public-track-001-C001 - Track a routed cargo that is in port (ABC123)

Kind: characterization
Evidence gate: observed-in-code
Discovery card: discovery/public-track/features/public-track-001.md
Evidence: Track.java:72; CargoTrackingViewAdapter.java:63,87,196

Boundary: POST public/track.xhtml
Input: trackingForm:trackingId_input=ABC123
Fixture: legacy startup sample data (SampleDataGenerator). JVM time zone UTC.

Observables:
- http.status
- result_present
- paragraphs (text of each p in #result)
- history_header
- events (icon check|flag, text)
- shows_not_found_text
- shows_next_expected_activity

Expected values: TO_BE_RECORDED. Test execution writes the golden from legacy output.
Scrub: collapse white space; decode HTML entities. No other scrub.
