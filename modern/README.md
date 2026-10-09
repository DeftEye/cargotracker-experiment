# cargo-tracker-modern

Strangler target for cargotracker. Pack: `architecture/cargotracker-modern/PACK.yaml` (cargotracker-springboot3-strangler@1, overnight-provisional BIND).

Java 21, Spring Boot 3.3. In-memory data seeded from the same raw inputs as legacy `SampleDataGenerator`.

```bash
mvn -f modern/pom.xml -B test
mvn -f modern/pom.xml -B -DskipTests package
java -Duser.timezone=UTC -jar modern/target/cargo-tracker-modern.jar   # http://localhost:8081/cargo-tracker
```

| Feature | Endpoint |
| --- | --- |
| cargo-monitor-001 | GET /cargo-tracker/rest/cargo |

Code in this module is a port of javaee/cargotracker, which is under the CDDL. Keep the licence notice with it.
