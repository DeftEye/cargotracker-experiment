
# Candidates — handling-report

Phase: A.
Status of every row: candidate.
This file is not a bind.

## handling-report-001

Provisional name: Submit a handling report by REST.

Confidence: observed-in-code.

Why it might belong: POST /rest/handling/reports is the HTTP intake for a handling attempt.

Evidence:
- Resource: src/main/java/net/java/cargotracker/interfaces/handling/rest/HandlingReportService.java:29
- Method: HandlingReportService.java:42
- Body constraints: src/main/java/net/java/cargotracker/interfaces/handling/rest/HandlingReport.java:14
- Bean validation responses: src/main/java/net/java/cargotracker/application/util/RestConfiguration.java:24
- Publish: src/main/java/net/java/cargotracker/infrastructure/messaging/jms/JmsApplicationEvents.java:68

Observed behaviour:
The method requires a non-null valid body.
completionTime must have length 16.
trackingId must have length of at least 4.
eventType must have length 4 to 7.
unLocode must have length 5.
voyageNumber is optional and must have length 4 to 5 when present.
The method parses completionTime with pattern yyyy-MM-dd HH:mm.
A bad time throws RuntimeException.
The method maps eventType with Enum.valueOf.
A voyage number is set only when the field is non-null.
The method publishes HandlingEventRegistrationAttempt.
The JMS send sets time to live to 1000 milliseconds.
The method does not store a handling event.

## handling-report-002

Provisional name: Submit a handling event from the mobile flow.

Confidence: observed-in-code.

Why it might belong: The mobile flow publishes the same attempt type.

Evidence:
- Flow bean: src/main/java/net/java/cargotracker/interfaces/handling/mobile/EventBackingBean.java:32
- Submit: EventBackingBean.java:221
- Tracking id filter: EventBackingBean.java:72
- Voyage rule: EventBackingBean.java:178
- Flow file: src/main/webapp/eventLogger/eventLogger-flow.xml

Observed behaviour:
init lists tracking ids that are routed and not claimed.
A LOAD event requires a voyage before the form can continue.
Other event types clear the voyage.
handleEventSubmission builds a HandlingEventRegistrationAttempt and publishes it.
The method then shows the message Event submitted.
The method does not store a handling event.

These two rows share a queue.
The consumer is slice handling-registration.
Do not treat the submit and the store as one behaviour.
