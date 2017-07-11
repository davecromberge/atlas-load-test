package com.outlyer.atlas.loadtest.mixed

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

import com.outlyer.atlas.loadtest.config.Config

class MixedLoadSimulation extends Simulation with Config {
  import QuerySimulation._
  import PublishSimulation._

  val simulations = Array(
    tags.inject(atOnceUsers(tagsUserCount)).protocols(queryHttp),
    fetch.inject(atOnceUsers(fetchUserCount)).protocols(queryHttp)
  ) ++ publish.map(_.inject(atOnceUsers(1)).protocols(publishHttp))

  setUp(simulations: _*)
}
