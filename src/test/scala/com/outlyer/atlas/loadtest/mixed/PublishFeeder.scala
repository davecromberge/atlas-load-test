package com.outlyer.atlas.loadtest.mixed

import io.gatling.core.feeder.Feeder
import io.gatling.core.feeder.Record

import scala.util.Random

/**
 * Feeder will use dynamic tags up to the number of `totalTagsCount`, after
 * which it will use static tags for each subsequent producer
 */
private[loadtest]
class PublishFeeder(
  name: String,
  node: Int,
  numMetrics: Int,
  totalTagsCount: Int,
  metricTagsCount: Int) extends Feeder[String] {

  private val (commonTagsCount, dynamicTagsCount) = {
    val x = node * metricTagsCount * numMetrics
    if (x > totalTagsCount)
      metricTagsCount -> 0
    else
      0 -> metricTagsCount
  }

  private val nodeId = s"node-i-$node"
  private val values = (0 until 7919).map(_ => Random.nextDouble()).toArray
  private var i = 0

  /**
   * Used to fill the remaining user tags after the maximum tag cardinality has
   * been reached
   */
  val commonTagMap = Map(
    "ol.cluster"   -> "foo-bar",
    "ol.eureka"    -> "foo-eureka",
    "ol.asg"       -> "foo-bar-v000",
    "ol.stack"     -> "bar",
    "ol.lang"      -> "us-en",
    "ol.runtime"   -> "jvm",
    "ol.region"    -> "us-east-1",
    "ol.vmtype"    -> "r3.2xlarge",
    "ol.build"     -> "jenkins",
    "ol.arn"       -> "us-west-task1",
    "ol.ami"       -> "awsatlasload",
    "ol.category"  -> "vm",
    "ol.region2"   -> "us-west-1",
    "ol.tier"      -> "1",
    "ol.eureka"    -> "eureka1",
    "ol.prov"      -> "terraform",
    "ol.region4"   -> "eu-east-1",
    "ol.deploy"    -> "spinnaker",
    "ol.region5"   -> "eu-west-1",
    "ol.currency"  -> "usd",
    "ol.region6"   -> "eu-central-1",
    "ol.timezone"  -> "utc",
    "ol.zone"      -> "us-east-1a",
    "ol.region3"   -> "us-central-1",
    "ol.class"     -> "A",
    "ol.billing"   -> "A",
    "ol.sourceid"  -> "foo-source",
    "ol.cloud"     -> "ec2",
    "ol.vmmem"     -> "64Gb")

  private lazy val commonTags = {
    val requiredTags = Map(
      "ol.app"       -> "loadtest",
      "ol.node"      -> s"$nodeId"
    ).toSeq

    val tagsStr =
      (requiredTags ++ commonTagMap.toSeq.take(commonTagsCount)).map { case (key, value) =>
        s"""      "$key": "$value""""
      }.mkString(",\n")

    s"""
      |{
      |   $tagsStr
      |}
    """.stripMargin
  }

  // name is dynamic and mandatory
  private def createDynamicTags(j: Int): Seq[String] = {
    val metricName = s"$nodeId.metric.$j"
    val nameTag = s""""name":         "$metricName""""
    val tail = (0 until dynamicTagsCount).map { i =>
      val key   = s"$nodeId.metric.$j.tag.$i.key"
      val value = s"$nodeId.metric.$j.tag.$i.value"
      s"""      "$key": "$value""""
    }
    nameTag +: tail
  }

  private def metric(j: Int, timestamp: Long, value: Double) = {
    val metricTags = createDynamicTags(j).mkString(",\n")
    s"""
      |{
      |  "tags": {
      |    $metricTags
      |  },
      |  "timestamp": $timestamp,
      |  "value": $value
      |}
    """.stripMargin
  }

  override def hasNext: Boolean = true

  override def next(): Record[String] = {
    val timestamp = System.currentTimeMillis()
    val payload = new StringBuilder
    payload.append("""{"tags":""").append(commonTags).append(""","metrics":[""")
    payload.append(metric(0, timestamp, 0))
    (1 until numMetrics).foreach { j =>
      i = Integer.remainderUnsigned(i + 1, values.length)
      val value = values(i)
      payload.append(",").append(metric(j, timestamp, value))
    }
    payload.append("]}")
    Map(name -> payload.toString())
  }
}
