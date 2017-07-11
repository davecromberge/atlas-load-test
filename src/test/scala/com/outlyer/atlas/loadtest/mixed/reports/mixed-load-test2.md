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
Metric count               | 2,000,000    |             |            |
Total tag count            | 10,000       |             |            |
Tags per metric            | 20           |             |            |

### Publisher Simulation Report

```
================================================================================
---- Global Information --------------------------------------------------------
> request count                                      42249 (OK=37813  KO=4436  )
> min response time                                      0 (OK=181    KO=0     )
> max response time                                  60160 (OK=23913  KO=60160 )
> mean response time                                  3345 (OK=1862   KO=15987 )
> std deviation                                       8950 (OK=2089   KO=23390 )
> response time 50th percentile                       1310 (OK=1343   KO=16    )
> response time 75th percentile                       2618 (OK=2511   KO=20384 )
> response time 95th percentile                      10024 (OK=7017   KO=60008 )
> response time 99th percentile                      60003 (OK=9563   KO=60010 )
> mean requests/sec                                  3.338 (OK=2.987  KO=0.35  )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                         16036 ( 38%)
> 800 ms < t < 1200 ms                                2226 (  5%)
> t > 1200 ms                                        19551 ( 46%)
> failed                                              4436 ( 10%)
---- Errors --------------------------------------------------------------------
> j.n.ConnectException: Connection refused: /34.229.73.195:7101    2462 (55.50%)
> status.find.in(200,304,201,202,203,204,205,206,207,208,209), b   1081 (24.37%)
ut actually found 503
> j.u.c.TimeoutException: Request timeout to /34.229.73.195:7101    541 (12.20%)
 after 60000ms
> j.n.ConnectException: connection timed out: /34.229.73.195:710    314 ( 7.08%)
1
> j.u.c.TimeoutException: Request timeout to not-connected after     22 ( 0.50%)
 60000ms
> j.i.IOException: Connection reset by peer                          16 ( 0.36%)
================================================================================

```

### Query Simulation Report

```
================================================================================
---- Global Information --------------------------------------------------------
> request count                                       7669 (OK=6555   KO=1114  )
> min response time                                      1 (OK=3      KO=1     )
> max response time                                  60029 (OK=28254  KO=60029 )
> mean response time                                  9717 (OK=9342   KO=11924 )
> std deviation                                       5012 (OK=3185   KO=10371 )
> response time 50th percentile                      10013 (OK=10013  KO=10017 )
> response time 75th percentile                      10017 (OK=10015  KO=10100 )
> response time 95th percentile                      12857 (OK=11934  KO=16405 )
> response time 99th percentile                      19278 (OK=15504  KO=60011 )
> mean requests/sec                                  0.645 (OK=0.552  KO=0.094 )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                           551 (  7%)
> 800 ms < t < 1200 ms                                   3 (  0%)
> t > 1200 ms                                         6001 ( 78%)
> failed                                              1114 ( 15%)
---- Errors --------------------------------------------------------------------
> status.find.in(200,304,201,202,203,204,205,206,207,208,209), b    997 (89.50%)
ut actually found 503
> j.n.ConnectException: Connection refused: /34.229.73.195:7101      71 ( 6.37%)
> j.u.c.TimeoutException: Request timeout to /34.229.73.195:7101     36 ( 3.23%)
 after 60000ms
> j.i.IOException: Connection reset by peer                           3 ( 0.27%)
> j.u.c.TimeoutException: Request timeout to not-connected after      3 ( 0.27%)
 60000ms
> j.n.ConnectException: connection timed out: /34.229.73.195:710      2 ( 0.18%)
1
> java.net.ConnectException: connection timed out: /34.229.73.19      2 ( 0.18%)
5:7101
================================================================================

```

### Other

Atlas memory committed: 17,33Gb after 4 hours
