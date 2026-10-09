
# Candidates — public-track

Phase: A.
Status of every row: candidate.
This file is not a bind.

## public-track-001

Provisional name: Track one cargo by tracking id.

Confidence: observed-in-code.

Why it might belong: The public page loads one cargo and its handling history.
The REST list does not do this.

Evidence:
- Bean: src/main/java/net/java/cargotracker/interfaces/tracking/web/Track.java:72
- Trim: Track.java:49
- Missing cargo message: Track.java:83
- Id list: Track.java:68
- View: src/main/webapp/public/track.xhtml

Observed behaviour:
setTrackingId trims the id.
onTrackById loads the cargo by tracking id.
A found cargo loads distinct handling events by completion time.
The bean wraps the cargo in CargoTrackingViewAdapter.
A missing cargo adds a Faces error and clears the cargo.
getTrackingIds ignores the query argument and returns every tracking id.
public/track.xhtml calls onTrackById and getTrackingIds.
admin/track.xhtml also calls completeTracking, onPointSelect, mapModel, and destinationCoordinates.
Those four names are not methods on Track.java.
The admin track page is a needs-SME mismatch.
