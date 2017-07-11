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
Total tag count            | 50,000       |             |            |
Tags per metric            | 20           |             |            |

### Publisher Simulation Report

```
================================================================================
---- Global Information --------------------------------------------------------
> request count                                      66369 (OK=66079  KO=290   )
> min response time                                     92 (OK=92     KO=10013 )
> max response time                                  13812 (OK=9947   KO=13812 )
> mean response time                                   354 (OK=310    KO=10291 )
> std deviation                                       1008 (OK=763    KO=831   )
> response time 50th percentile                        144 (OK=144    KO=10023 )
> response time 75th percentile                        186 (OK=184    KO=10049 )
> response time 95th percentile                        767 (OK=744    KO=13011 )
> response time 99th percentile                       5355 (OK=4549   KO=13509 )
> mean requests/sec                                   3.34 (OK=3.325  KO=0.015 )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                         63207 ( 95%)
> 800 ms < t < 1200 ms                                 369 (  1%)
> t > 1200 ms                                         2503 (  4%)
> failed                                               290 (  0%)
---- Errors --------------------------------------------------------------------
> status.find.in(200,304,201,202,203,204,205,206,207,208,209), b    290 (100.0%)
ut actually found 503
================================================================================
```

### Query Simulation Report

```

================================================================================
---- Global Information --------------------------------------------------------
> request count                                      19438 (OK=18084  KO=1354  )
> min response time                                      3 (OK=3      KO=10013 )
> max response time                                  15846 (OK=15846  KO=14386 )
> mean response time                                  6528 (OK=6260   KO=10109 )
> std deviation                                       3809 (OK=3814   KO=496   )
> response time 50th percentile                       9326 (OK=6595   KO=10015 )
> response time 75th percentile                      10012 (OK=10012  KO=10015 )
> response time 95th percentile                      10015 (OK=10015  KO=10205 )
> response time 99th percentile                      10627 (OK=10281  KO=13113 )
> mean requests/sec                                  0.989 (OK=0.92   KO=0.069 )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                           625 (  3%)
> 800 ms < t < 1200 ms                                 586 (  3%)
> t > 1200 ms                                        16873 ( 87%)
> failed                                              1354 (  7%)
---- Errors --------------------------------------------------------------------
> status.find.in(200,304,201,202,203,204,205,206,207,208,209), b   1354 (100.0%)
ut actually found 503
================================================================================
```

### Other

Atlas memory committed: 16,1Gb
