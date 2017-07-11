### Simulations

Simultaneous queries to:

```
/api/v2/fetch?s=now-1m&&q=ol.app,loadtest,:eq,(,ol.node,),:by
/api/v1/tags
```
While N producers send requests of given payload metrics at the set rate.

### Setup

Role         | Machine         | RAM           | CPUs      |
---          | ---             | ---           | ---       |
Atlas        | r3.2x large     | 61Gb          | 8         |
Query load   | m3.x large      | 15Gb          | 4         |
Publish load | m3.x large      | 15Gb          | 4         |

### Test Inputs

| Producer Input           | Value        | Query Input | Value      |
---                        | ---          | ---         | ---        |
Producers                  | 100          | Tags users  | 10         |
Metrics/payload            | 10,000       | Fetch users | 30         |
Metric count               | 500,000      |             |            |
Total tag count            | 2,000        |             |            |
Tags per metric            | 30           |             |            |

### Publisher Simulation Report

```

```

### Query Simulation Report

```

```

### Other

Atlas memory committed: 16,11Gb after 10 hours
