# Overnight bind - public-track

OVERNIGHT_PROVISIONAL - a human must confirm or change these decisions.
Charter: overnight/CHARTER.md

| Feature | Decision | Reason | Evidence |
| --- | --- | --- | --- |
| public-track-001 | accept | Public read-only page. RECORD is possible by form post. | Track.java:72, public/track.xhtml |

Scope cut:
- admin/track.xhtml is out of scope. It calls four methods that Track.java does not declare. It stays a needs-SME item.
- The Google map panel is out of scope. It needs an external map API with no key. Characterization does not pin it.

Open questions from Phase A, now answered from code and from the running legacy server:
- "Which adapter fields does the page render?" trackingId, statusText, destination, eta, misdirected, and events (description and expected). nextExpectedActivity is in an f:facet in a panelGroup. That facet does not render.
- The not-found message is queued (Track.java:83). The public template has no messages component. The user sees no result block and no message.

Morning action: confirm the row, or edit discovery/public-track/MANIFEST.yaml and remove `bind_source: overnight_provisional`.
