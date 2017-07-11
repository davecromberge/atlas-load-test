package com.outlyer.atlas.loadtest.config

import com.typesafe.config.{ConfigException, ConfigFactory}
import scala.concurrent.duration._
import scala.util.Try

trait Config {

  private[this] val config = ConfigFactory.load()

  /**
   * Gets required string from config file or throws an exception if string not found.
   *
   * @param path path to string
   * @return string fetched by path
   */
  def getRequiredString(path: String) = {
    Try(config.getString(path)).getOrElse {
      handleError(path)
    }
  }

  /**
   * Gets required int from config file or throws an exception if int not found.
   *
   * @param path path to int
   * @return int fetched by path
   */
  def getRequiredInt(path: String) = {
    Try(config.getInt(path)).getOrElse {
      handleError(path)
    }
  }

  /**
   * Gets required string list from config file or throws an exception if string list not found.
   *
   * @param path path to string list
   * @return string list fetched by path
   */
  def getRequiredStringList(path: String) = {
    Try(config.getStringList(path)).getOrElse {
      handleError(path)
    }
  }

  private[this] def handleError(path: String) = {
    val errMsg = s"Missing required configuration entry: $path"
    throw new ConfigException.Missing(errMsg)
  }

  /**
   *  The Atlas web api endpoint address
   */
  val baseURL = getRequiredString("simulation.host")

  val testDuration = Duration(getRequiredInt("simulation.duration"), HOURS)

  val publishApi = getRequiredString("simulation.publish.api")

  val tagsApi = getRequiredString("simulation.tags.api")

  val fetchApi = getRequiredString("simulation.fetch.api")
}
