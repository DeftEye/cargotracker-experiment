# public-track-001 - Track one cargo by tracking id

Status: documented (OVERNIGHT_PROVISIONAL bind)
Confidence: observed-in-code
Slice: public-track

## Boundary

Page: /cargo-tracker/public/track.xhtml
GET shows the form. A form POST with field `trackingForm:trackingId_input` runs Track.onTrackById.
No authentication.

Code:
- src/main/java/net/java/cargotracker/interfaces/tracking/web/Track.java:47-89
- src/main/java/net/java/cargotracker/interfaces/tracking/web/CargoTrackingViewAdapter.java
- src/main/webapp/public/track.xhtml

## Observed behaviour

1. setTrackingId trims leading and trailing spaces (Track.java:49).
2. onTrackById finds the cargo by tracking id (Track.java:73).
3. Found cargo:
   - The bean loads the handling history of the cargo, distinct, ordered by completion time (HandlingHistory.java:33).
   - The page shows a block with id "result" that contains, in this order:
     - "Cargo <trackingId> is currently <statusText>"
     - "Estimated time of arrival in <destination name>: <eta>"
     - "Cargo is misdirected." only when the stored delivery is misdirected.
     - "Handling History" and one line per event, only when there is one or more event.
   - Each event line has a green check icon (fa-check) when the itinerary expects the event, else a red flag icon (fa-flag) (track.xhtml:31-38, Itinerary.java:56).
4. Missing cargo: the bean queues a Faces error "Cargo with tracking ID: <id> not found." and clears the cargo (Track.java:81-87). The public template has no messages component. The user sees the form only. No result block. No message text.
5. The next expected activity text is inside an f:facet in a panelGroup (track.xhtml:25). JSF does not render that facet. The text never shows.

## Text rules (CargoTrackingViewAdapter)

Status text (getStatusText):
- IN_PORT: "In port <last known location name>"
- ONBOARD_CARRIER: "Onboard voyage <current voyage number>"
- CLAIMED: "Claimed"
- NOT_RECEIVED: "Not received"
- UNKNOWN: "Unknown"

ETA (getEta): "?" when the stored ETA is null. Else format "MM/dd/yyyy hh:mm a z".
The ETA is the final leg unload time only when the cargo is ROUTED and not misdirected (Delivery.java:252).

Event description (getDescription), with time format "MM/dd/yyyy hh:mm a z":
- LOAD: "Loaded onto voyage <v> in <location>, at <time>."
- UNLOAD: "Unloaded off voyage <v> in <location>, at <time>."
- RECEIVE: "Received in <location>, at <time>."
- CLAIM: "Claimed in <location>, at <time>."
- CUSTOMS: "Cleared customs in <location>, at <time>."

The time zone in the text is the JVM default time zone. The RECORD runs use UTC.

## Observed sample output (legacy, 2026-10-09, UTC)

| Id | Status | ETA | Misdirected | Events (expected / flagged) |
| --- | --- | --- | --- | --- |
| ABC123 | In port New York | 03/12/2016 12:00 AM UTC | no | 3 / 0 |
| JKL567 | Onboard voyage 0100S | ? | yes | 3 / 1 |
| DEF789 | Not received | ? | no | 0 / 0 |
| MNO456 | Claimed | ? | no | 5 / 0 |
| NOPE99 | no result block | - | - | - |

JKL567: the last LOAD in New York on voyage 0100S is flagged. The itinerary loads in New York on voyage 0200T.

## Not in this card

- The Google map panel (external API, no key).
- The PrimeFaces autocomplete list (getTrackingIds). It is a client widget helper.
- admin/track.xhtml (needs-SME mismatch).
- Live updates (slice realtime tracking).
