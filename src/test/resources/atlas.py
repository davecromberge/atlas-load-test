#!/usr/bin/env python

"""
    Java Plugin - Monitors Java process via JMX
"""

import subprocess
import json

# Path to Java binary. On Windows, you should use forward slashes as the path separator
JAVA_BIN = "java"

# Set the JMX URL Endpoint to Connect To
JMX_URL = 'service:jmx:rmi:///jndi/rmi://localhost:7500/jmxrmi'

# Set JMX Username, if authentication required
JMX_USERNAME = ''

# Set JMX Password, if authentication required
JMX_PASSWORD = ''

# Block store metrics
BLOCK_METRICS = "atlas.block.arrayBytes=metrics:name=atlas.block.arrayBytes/Value;" +\
          "atlas.block.arrayCount=metrics:name=atlas.block.arrayCount/Value;" +\
          "atlas.block.constantBytes=metrics:name=atlas.block.constantBytes/Value;" +\
          "atlas.block.constantCount=metrics:name=atlas.block.constantCount/Value;" +\
          "atlas.block.sparseBytes=metrics:name=atlas.block.sparseBytes/Value;" +\
          "atlas.block.sparseCount=metrics:name=atlas.block.sparseCount/Value;"

QUERY_METRICS = "atlas.db.queryMetrics=metrics:name=atlas.db.queryMetrics/MeanRate;" +\
          "atlas.db.queryInputDatapoints=metrics:name=atlas.db.queryInputDatapoints/MeanRate;" +\
          "atlas.db.queryLines=metrics:name=atlas.db.queryLines/MeanRate;" +\
          "atlas.db.aggrBlocks=metrics:name=atlas.db.aggrBlocks/MeanRate;" +\
          "atlas.db.queryLines=metrics:name=atlas.db.queryLines/MeanRate;" +\
          "atlas.db.queryOutputDatapoints=metrics:name=atlas.db.queryOutputDatapoints/MeanRate;"

INDEX_METRICS = "atlas.index.pendingUpdates=metrics:name=atlas.index.pendingUpdates/Value;" +\
          "atlas.index.rebuildTime.statistic-activeTasks=metrics:name=atlas.index.rebuildTime.statistic-activeTasks/Value;" +\
          "atlas.index.rebuildTime.statistic-duration=metrics:name=atlas.index.rebuildTime.statistic-duration/Value;"

ACTOR_METRICS = "akka.queue.insert.path-db=metrics:name=akka.queue.insert.path-db/MeanRate;" +\
          "akka.queue.insert.path-publish=metrics:name=akka.queue.insert.path-publish/MeanRate;" +\
          "akka.queue.wait.path-db=metrics:name=akka.queue.wait.path-db/99thPercentile;" +\
          "akka.queue.wait.path-publish=metrics:name=akka.queue.wait.path-publish/99thPercentile;" +\
          "akka.queue.size.path-db=metrics:name=akka.queue.size.path-db/Value;" +\
          "akka.queue.size.path-publish=metrics:name=akka.queue.size.path-publish/Value;"

METRICS = BLOCK_METRICS +\
          QUERY_METRICS +\
          INDEX_METRICS +\
          ACTOR_METRICS
def getMetrics():
    command = [JAVA_BIN, '-jar', '../embedded/lib/jmxquery.jar', '-url', JMX_URL, '-metrics', METRICS, '-json']
    if JMX_USERNAME:
        command.extend(['-username', JMX_USERNAME, '-password', JMX_PASSWORD])
    jsonOutput = ""

    try:
        jsonOutput = subprocess.check_output(command)
    except:
        print "Error connecting to JMX URL '" + JMX_URL + "'. Please check URL and that JMX is enabled on Java process"
        exit(2)

    metrics = json.loads(jsonOutput)

    # Print Nagios output
    output = "OK | "
    for metric in metrics:
        if 'value' in metric.keys():
            output += metric['metricName'] + "=" + metric['value'] + ";;;; "
    print output


getMetrics()
