# ADR 0001 - Spring Boot strangler for the tracking read paths

Status: accepted (OVERNIGHT_PROVISIONAL_BIND - human must re-BIND)
Pack: cargotracker-springboot3-strangler@1

## Context

Legacy is a Java EE 7 WAR (JSF 2.2, PrimeFaces 5.2, JAX-RS, EJB, JPA on embedded Derby).
It needs a Java EE 7 server. GlassFish 4.1 downloads are gone. The run used Payara 5 with extra setup.
The first two slices read data only: the JSON cargo list and the public track page.

## Decision

1. Target: Java 21, Spring Boot 3.3, Spring MVC, Thymeleaf. Maven build in modern/.
2. Style: strangler. The modern app runs beside legacy on port 8081 with the same context path /cargo-tracker.
3. Contract: preserve-wire. The characterization goldens are the contract.
4. Data: seeded mirror. Modern seeds the same raw sample inputs and derives delivery with ported rules. Modern does not store output values.
5. Domain: port Delivery, Itinerary, Leg, RouteSpecification, HandlingEvent, HandlingHistory, and HandlingActivity as plain Java. Keep the legacy equality rules.

## Why not other options

- Keep JSF on Jakarta Faces: smaller jump, but the server dependency stays. That does not meet the aim of the run.
- Share the legacy database: the Derby database is in-process and reloads at each start. There is nothing to share.
- JPA in modern now: no feature in this batch writes data. Persistence comes with the first write slice.

## Consequences

- The modern domain core can serve later slices (booking, handling) when they move.
- When a write slice moves, a new pack version must add a data strategy.
- Legacy quirks stay (silent not-found, hidden next-activity text). The goldens pin them.
