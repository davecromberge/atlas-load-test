package com.outlyer.atlas.loadtest.mixed

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

import com.outlyer.atlas.loadtest.config.Config

object PublishSimulation extends Config {
  /**
   * The number of unique metrics to publish
   */
  val metricCount = 1000000

  /**
   * The number of metrics to include per request
   */
  val payloadCount = 10000

  /**
   * Each simulation user will send a request consisting of `payloadCount` metrics
   */
  val producerCount = Integer.divideUnsigned(metricCount, payloadCount)

  /**
   * The approximate total number of tag keys to generate
   */
  val totalTagsCount = 500

  /**
   *  The number of tags per metric, where provision will be made for 3 required
   *  tags for load tests to exercise correctly:
   *      ol.app = loadtest
   *      ol.node = node-i-0
   *      name = metric-name */
  val metricTagsCount = 20

  val publishHttp = http
    .baseURL(baseURL)
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("load-test")
    .header("Content-Encoding", "gzip")

  val publish = (0 until producerCount).map { i =>
    scenario(s"publish-$i")
      .during(testDuration) {
        exec(feed(new PublishFeeder("test", i, payloadCount, totalTagsCount, metricTagsCount)))
          .exec(http("publish").post(publishApi).body(StringBody("""${test}"""))
          .processRequestBody(gzipBody))
          .pace(30 seconds)
      }
  }
}

class PublishSimulation extends Simulation with Config {
  import PublishSimulation._

  setUp(publish.map(_.inject(atOnceUsers(1)).protocols(publishHttp)): _*)
}
