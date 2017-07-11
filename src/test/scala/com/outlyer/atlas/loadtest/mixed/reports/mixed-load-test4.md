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
Metric count               | 1,000,000    |             |            |
Total tag count            | 6,000        |             |            |
Tags per metric            | 30           |             |            |

### Publisher Simulation Report

```
================================================================================
---- Global Information --------------------------------------------------------
> request count                                       4333 (OK=3697   KO=636   )
> min response time                                    199 (OK=199    KO=4585  )
> max response time                                  21618 (OK=20082  KO=21618 )
> mean response time                                 10104 (OK=10088  KO=10193 )
> std deviation                                        912 (OK=894    KO=1005  )
> response time 50th percentile                      10016 (OK=10015  KO=10019 )
> response time 75th percentile                      10018 (OK=10016  KO=10020 )
> response time 95th percentile                      10138 (OK=10117  KO=10678 )
> response time 99th percentile                      14440 (OK=14401  KO=14437 )
> mean requests/sec                                  0.934 (OK=0.797  KO=0.137 )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                            10 (  0%)
> 800 ms < t < 1200 ms                                   0 (  0%)
> t > 1200 ms                                         3687 ( 85%)
> failed                                               636 ( 15%)
---- Errors --------------------------------------------------------------------
> status.find.in(200,304,201,202,203,204,205,206,207,208,209), b    633 (99.53%)
ut actually found 503
> Check didn't succeed by the time the server closed the sse          3 ( 0.47%)
================================================================================
```

### Query Simulation Report

```
================================================================================
---- Global Information --------------------------------------------------------
> request count                                       4333 (OK=3697   KO=636   )
> min response time                                    199 (OK=199    KO=4585  )
> max response time                                  21618 (OK=20082  KO=21618 )
> mean response time                                 10104 (OK=10088  KO=10193 )
> std deviation                                        912 (OK=894    KO=1005  )
> response time 50th percentile                      10016 (OK=10015  KO=10019 )
> response time 75th percentile                      10018 (OK=10016  KO=10020 )
> response time 95th percentile                      10138 (OK=10117  KO=10678 )
> response time 99th percentile                      14440 (OK=14401  KO=14437 )
> mean requests/sec                                  0.934 (OK=0.797  KO=0.137 )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                            10 (  0%)
> 800 ms < t < 1200 ms                                   0 (  0%)
> t > 1200 ms                                         3687 ( 85%)
> failed                                               636 ( 15%)
---- Errors --------------------------------------------------------------------
> status.find.in(200,304,201,202,203,204,205,206,207,208,209), b    633 (99.53%)
ut actually found 503
> Check didn't succeed by the time the server closed the sse          3 ( 0.47%)
================================================================================
```

### Other

Atlas memory committed: 16,11Gb after 10 hours
