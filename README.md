## Load tests

Adapted from [load test](https://github.com/brharrington/atlas-load-test) by B. Harrington.

Simulates a mixed read write load against a single Atlas instance.  The
[Gatling](http://gatling.io/documentation/) load testing tool is used to run
the scenarios.

### Configuration

```
simulation {
    # Url to atlas host:
    host = "http://x.x.x.x:7101"

    # Duration in hours
    duration = 10

    # tags endpoint
    tags.api = "/api/v1/tags"

    # publish endpoint
    publish.api = "/api/v1/publish"

    # fetch endpoint
    fetch.api = "/api/v2/fetch"
}
```

Atlas server configuration can be found in `resources/memory.conf`

### Generating reports

After a test:

    sbt gatling:generateReport
