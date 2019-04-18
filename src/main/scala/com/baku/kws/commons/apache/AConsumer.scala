package com.baku.kws.commons.apache

import java.time.{Duration => JDuration}
import java.util.Collections

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.collection.JavaConverters._
import scala.concurrent.duration._

class AConsumer(name: String, topic: String, msgConsumingTime: FiniteDuration = 100.millis) extends CommonProps with LazyLogging {

  kafkaProps.setProperty("group.id", "test")
  private val consumer = new KafkaConsumer[String, String](kafkaProps)
  consumer.subscribe(Collections.singletonList(topic))

  def consume(): Unit = {
    try {
      logger.info(s"$name is consuming...")
      val dur = JDuration.ofNanos(1.second.toNanos)
      val records = consumer.poll(dur).iterator().asScala.toSeq
      records.foreach({ record =>
        val key = record.key()
        val value = record.value()
        val partition = record.partition()
        Thread.sleep(msgConsumingTime.toMillis)
        logger.info(s"$name CONSUMED key: $key value: $value partition: $partition")
        consumer.commitAsync()
      })

    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def close(): Unit = consumer.close()

}
