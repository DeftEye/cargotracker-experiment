# Side-by-side demo

Three processes. Paths match the overnight VM; change them for your machine.

```bash
# 1. Legacy (Java EE 7 on Payara 5, JDK 11, port 8080)
export AS_JAVA=/opt/tools/jdk-11.0.32.1+1
/opt/tools/payara5/bin/asadmin start-domain
/opt/tools/payara5/bin/asadmin deploy --contextroot cargo-tracker --force=true target/cargo-tracker.war

# 2. Modern (Spring Boot 3 on Java 21, port 8081)
mvn -f modern/pom.xml -B -DskipTests package
java -Duser.timezone=UTC -jar modern/target/cargo-tracker-modern.jar

# 3. Viewer (port 8090, serve from the repository root)
python3 -m http.server 8090
# open http://localhost:8090/overnight/demo/side-by-side.html
```

Legacy setup notes (one time): see overnight/CONTEXT_GATE.md (Derby jar, theme jar, https mirrors, X-Frame-Options off, JVM time zone UTC).
