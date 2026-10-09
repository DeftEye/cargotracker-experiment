# Method coverage

Banner: ESTATE_SCAN_INCOMPLETE

This pass touched, skipped, or could not see each method family.
Zero-diff on a later pass is not completeness.

| Method family | Result | Evidence |
| --- | --- | --- |
| HTTP/REST | touched | GET /rest/cargo, POST /rest/handling/reports, GET /rest/graph-traversal/shortest-path |
| SOAP | not seen | src/test/soapui is a test project. No SOAP listener was found under src/main. |
| Message listeners | touched | Five JMS consumers under infrastructure/messaging/jms |
| Schedulers / batch / CLI | touched | UploadDirectoryScanner and EventFilesProcessorJob. No CLI main was found. |
| Gateway / RAML / OpenAPI | not seen | No RAML or OpenAPI file was found. JAX-RS annotations are the contract. |
| UI adapters | touched and deprioritised | Faces beans are noted. REST or queue boundaries stay the primary seams where they exist. |

## What this pass did not search

- Host process trees, listeners, and packages. See DEFERRED_RUNTIME_DISCOVERY in overnight/CONTEXT_GATE.md.
- Runtime files under /tmp/uploads.
- Vendor binaries.
- The body of Delivery.deriveDeliveryProgress. The inspection slice calls it. The rules are an unscanned hint.
- GraphDao behind the pathfinder resource. The package is an unscanned hint.
- The WebLogic web.xml twin under src/weblogic.
- persistence.xml and the Derby datasource beyond the declaration in web.xml.
- Static page src/main/webapp/public/about.xhtml.
