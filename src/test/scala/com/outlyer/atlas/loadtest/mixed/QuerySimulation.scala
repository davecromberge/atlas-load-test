package com.outlyer.atlas.loadtest.mixed

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

import com.outlyer.atlas.loadtest.config.Config

object QuerySimulation extends Config {

  // number of simulated users querying tags
  val tagsUserCount = 10

  // number of simulated users fetching raw data
  val fetchUserCount = 30

  val queryHttp = http
    .baseURL(baseURL)
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val tags = scenario("tags")
    .during(testDuration) {
      exec(http("tags")
        .get(s"$tagsApi/name"))
        .pause(10 seconds, 120 seconds)
    }

  // fetch every single nodes data for the last 10 minutes
  val fetch = scenario("fetch")
    .during(testDuration) {
      exec(sse("fetch")
            .open(s"$fetchApi?s=now-1m&&q=ol.app,loadtest,:eq,(,ol.node,),:by")
            .check(wsAwait.within(30).until(1).regex(""""message":"operation complete"""")))
      .pause(10 seconds, 120 seconds)
      .exec(sse("Close SSE").close())
    }
}

class QuerySimulation extends Simulation with Config {
  import QuerySimulation._
  setUp(
    tags.inject(atOnceUsers(tagsUserCount)).protocols(queryHttp),
    fetch.inject(atOnceUsers(fetchUserCount)).protocols(queryHttp))
}
