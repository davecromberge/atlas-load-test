enablePlugins(GatlingPlugin)

scalaVersion := "2.11.8"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

libraryDependencies ++= Seq(
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2" % "test",
  "io.gatling"            % "gatling-test-framework"    % "2.2.2" % "test",
  "com.typesafe.play"    %% "play-json"                 % "2.5.9")

javaOptions in Gatling := Seq("-Xms2G", "-Xmx10G")
