# public-track-001-C007 - Empty post on the same page after a found cargo

Kind: characterization
Evidence gate: observed-in-code
Discovery card: discovery/public-track/features/public-track-001.md
Evidence: track.xhtml:17 (autoComplete required="true" forceSelection="true"); Track.java:30 (@ViewScoped)
Found by: Verification screen recording, 2026-10-09. In a browser, an unknown id typed into the autocomplete is cleared on the client (forceSelection), so the form posts an empty value.

Boundary: POST public/track.xhtml twice on the same page view
Input: first trackingForm:trackingId_input=JKL567, then trackingForm:trackingId_input="" (empty)
Fixture: legacy startup sample data. JVM time zone UTC.

Observables:
- http.status
- result_present
- paragraphs
- history_header
- events
- shows_not_found_text
- shows_next_expected_activity
- input_marked_error (ui-state-error class on the tracking id input)

Expected values: TO_BE_RECORDED.
Scrub: collapse white space; decode HTML entities. No other scrub.
