
# SME brief — public-track

## What the scan found

Public tracking is a Faces bean on the domain repositories.
There is no matching single-cargo REST method in this repository.

## Boundaries

In this slice: look up one tracking id and show handling history.
Out of this slice: the all-cargo JSON list and the WebSocket push.

## Recommended bind

Accept public-track-001.
The row has a callable screen boundary.
Keep it separate from cargo-monitor.

## Open questions

Which fields does CargoTrackingViewAdapter expose?
Does a blank id throw inside TrackingId?
Does admin/track.xhtml fail because completeTracking and onPointSelect are absent from Track?

## Stop

Phase A stops here.
Do not deepen this row until a human bind.
