
# Candidates — cargo-inspection

Phase: A.
Status of every row: candidate.
This file is not a bind.

## cargo-inspection-001

Provisional name: Inspect a cargo after a handled message.

Confidence: observed-in-code.

Why it might belong: CargoHandledQueue starts delivery progress derivation.

Evidence:
- Consumer: src/main/java/net/java/cargotracker/infrastructure/messaging/jms/CargoHandledConsumer.java:22
- Inspect: src/main/java/net/java/cargotracker/application/internal/DefaultCargoInspectionService.java:35
- Queue declaration: src/main/webapp/WEB-INF/web.xml:51

Observed behaviour:
The consumer reads a text message and calls inspectCargo.
A missing cargo logs a warning and returns.
A found cargo loads handling history and calls deriveDeliveryProgress.
A misdirected cargo publishes MisdirectedCargoQueue.
An unloaded-at-destination cargo publishes DeliveredCargoQueue.
The service stores the cargo.
The service fires a CargoInspected CDI event.
A JMSException is logged and not rethrown.

## cargo-inspection-002

Provisional name: Log a misdirected cargo id.

Confidence: observed-in-code.

Why it might belong: MisdirectedCargoQueue has a consumer.

Evidence:
- Consumer: src/main/java/net/java/cargotracker/infrastructure/messaging/jms/MisdirectedCargoConsumer.java:11
- Sender: src/main/java/net/java/cargotracker/infrastructure/messaging/jms/JmsApplicationEvents.java:46

Observed behaviour:
The consumer logs the tracking id.
The consumer does not change cargo state.

## cargo-inspection-003

Provisional name: Log a delivered cargo id.

Confidence: observed-in-code.

Why it might belong: DeliveredCargoQueue has a consumer.

Evidence:
- Consumer: src/main/java/net/java/cargotracker/infrastructure/messaging/jms/DeliveredCargoConsumer.java:11
- Sender: JmsApplicationEvents.java:57

Observed behaviour:
The consumer logs the tracking id.
The consumer does not change cargo state.

## cargo-inspection-004

Provisional name: Push inspection JSON to tracking sockets.

Confidence: observed-in-code.

Why it might belong: The WebSocket observes CargoInspected.

Evidence:
- Endpoint: src/main/java/net/java/cargotracker/interfaces/booking/socket/RealtimeCargoTrackingService.java:27
- Observer: RealtimeCargoTrackingService.java:47

Observed behaviour:
Clients connect to /tracking.
The idle timeout is five minutes.
On CargoInspected the bean sends JSON with trackingId, origin, destination, lastKnownLocation, and transportStatus.
A send error is logged per session.

## cargo-inspection-005

Provisional name: Log a rejected registration attempt.

Confidence: needs-SME.

Why it might belong: A consumer and a queue exist for rejected attempts.

Evidence:
- Consumer: src/main/java/net/java/cargotracker/infrastructure/messaging/jms/RejectedRegistrationAttemptsConsumer.java:11
- Queue declaration: src/main/webapp/WEB-INF/web.xml:70

Observed behaviour:
The consumer logs a tracking id.
This pass found no Java sender for RejectedRegistrationAttemptsQueue.
The row stays needs-SME.
