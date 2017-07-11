
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
Total tag count            | 12,000       |             |            |
Tags per metric            | 30           |             |            |

### Publisher Simulation Report

```
================================================================================
---- Global Information --------------------------------------------------------
> request count                                     120100 (OK=119306 KO=794   )
> min response time                                    110 (OK=110    KO=10014 )
> max response time                                  15066 (OK=14880  KO=15066 )
> mean response time                                   507 (OK=440    KO=10436 )
> std deviation                                       1272 (OK=982    KO=914   )
> response time 50th percentile                        193 (OK=192    KO=10025 )
> response time 75th percentile                        256 (OK=253    KO=10197 )
> response time 95th percentile                       1990 (OK=1583   KO=12596 )
> response time 99th percentile                       7175 (OK=5377   KO=13757 )
> mean requests/sec                                  3.334 (OK=3.312  KO=0.022 )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                        110593 ( 92%)
> 800 ms < t < 1200 ms                                1296 (  1%)
> t > 1200 ms                                         7417 (  6%)
> failed                                               794 (  1%)
---- Errors --------------------------------------------------------------------
> status.find.in(200,304,201,202,203,204,205,206,207,208,209), b    794 (100.0%)
ut actually found 503
================================================================================
```

### Query Simulation Report

```
================================================================================
---- Global Information --------------------------------------------------------
> request count                                      33928 (OK=29610  KO=4318  )
> min response time                                      4 (OK=4      KO=10011 )
> max response time                                  60004 (OK=20593  KO=60004 )
> mean response time                                  9408 (OK=9293   KO=10196 )
> std deviation                                       2243 (OK=2314   KO=1448  )
> response time 50th percentile                      10017 (OK=10017  KO=10019 )
> response time 75th percentile                      10018 (OK=10017  KO=10020 )
> response time 95th percentile                      10104 (OK=10026  KO=10968 )
> response time 99th percentile                      12941 (OK=12766  KO=13483 )
> mean requests/sec                                  0.941 (OK=0.822  KO=0.12  )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                           211 (  1%)
> 800 ms < t < 1200 ms                                  39 (  0%)
> t > 1200 ms                                        29360 ( 87%)
> failed                                              4318 ( 13%)
---- Errors --------------------------------------------------------------------
> status.find.in(200,304,201,202,203,204,205,206,207,208,209), b   4315 (99.93%)
ut actually found 503
> java.net.ConnectException: connection timed out: /34.229.73.19      3 ( 0.07%)
5:7101
================================================================================
```

### Other

Atlas memory committed: 16,11Gb after 10 hours
