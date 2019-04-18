package com.baku.kws.excercises

import java.util

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.Partitioner
import org.apache.kafka.common.Cluster
import org.apache.kafka.common.record.InvalidRecordException
import org.apache.kafka.common.utils.Utils

import scala.collection.JavaConverters._

class MyCustomPartitioner extends Partitioner with LazyLogging {

  private var sensorName = ""

  override def partition(topic: String, key: Any, keyBytes: Array[Byte], value: Any, valueBytes: Array[Byte], cluster: Cluster): Int = {
    val partitions = cluster.partitionsForTopic(topic).asScala
    val numPartitions = partitions.size
    val sp = Math.abs(numPartitions * 0.3)

    if ((keyBytes == null) || (!key.isInstanceOf[String])) throw new InvalidRecordException("All messages must have sensor name as key")

    val p = if (key.asInstanceOf[String] == sensorName) Utils.toPositive(Utils.murmur2(valueBytes)) % sp
    else Utils.toPositive(Utils.murmur2(keyBytes)) % (numPartitions - sp) + sp

    logger.info(s"----> Key = ${key.asInstanceOf[String]} Partition = $p sensorName: $sensorName numPartitions: $numPartitions")

    p.toInt
  }

  override def close(): Unit = ()

  override def configure(configs: util.Map[String, _]): Unit = {
    sensorName = configs.get("sensor.name").toString
  }
}
