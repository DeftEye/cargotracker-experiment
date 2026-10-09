# public-track-001-C005 - Track an id that does not exist (NOPE99)

Kind: characterization
Evidence gate: observed-in-code
Discovery card: discovery/public-track/features/public-track-001.md
Evidence: Track.java:81-87; WEB-INF/templates/common/public.xhtml (no messages component)

Boundary: POST public/track.xhtml
Input: trackingForm:trackingId_input=NOPE99
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
