
# Candidates — handling-registration

Phase: A.
Status of every row: candidate.
This file is not a bind.

## handling-registration-001

Provisional name: Store a handling event from the registration queue.

Confidence: observed-in-code.

Why it might belong: This consumer is the first code that stores a handling event.

Evidence:
- Consumer: src/main/java/net/java/cargotracker/infrastructure/messaging/jms/HandlingEventRegistrationAttemptConsumer.java:18
- Queue: HandlingEventRegistrationAttemptConsumer.java:22
- Service: src/main/java/net/java/cargotracker/application/internal/DefaultHandlingEventService.java:30
- Event types: src/main/java/net/java/cargotracker/domain/model/handling/HandlingEvent.java:86
- Queue declaration: src/main/webapp/WEB-INF/web.xml:75

Observed behaviour:
The consumer reads an object message.
The consumer calls registerHandlingEvent.
The service creates a handling event in a factory.
The service stores the event.
The service publishes cargoWasHandled with the tracking id on CargoHandledQueue.
A JMSException or CannotCreateHandlingEventException becomes RuntimeException.
The comment says poison messages go to the dead-letter queue.
The service comment says the cargo aggregate is not updated here.
Event types are LOAD, UNLOAD, RECEIVE, CLAIM, and CUSTOMS.
LOAD and UNLOAD require a voyage.
