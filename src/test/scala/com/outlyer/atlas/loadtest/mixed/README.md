Simulates multiple queries for dimensions and raw data, while publishing
a consistent load spread over N producers.

For querying, the number of simultaneous users differ between generating tags
and requesting graphs - and the requests are balanced between the two profiles.

### Publish configuration

Configuration is inline, in `PublishSimulation.scala`.

### Query configuration

Configuration is inline, in `QuerySimulation.scala`.

### Running mixed simulation

Runs both publish and query simulations:

    gatling:testOnly com.outlyer.atlas.loadtest.mixed.MixedLoadSimulation
