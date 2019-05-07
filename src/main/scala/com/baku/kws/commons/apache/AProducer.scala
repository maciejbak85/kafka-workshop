package com.baku.kws.commons.apache

import java.util.Properties

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class AProducer(topic: String, partitionOpt: Option[Int] = None, customProps: Option[Properties] = None) extends CommonProps with LazyLogging {

  val producer = new KafkaProducer[String, String](customProps.getOrElse(kafkaProps))

  def sendRecord(key: String, value: String): Unit =
    sendRecord(Some(key), value)

  def sendRecord(keyOpt: Option[String], value: String): Unit = try {
    logger.info(s"Sending message $keyOpt $value to $topic partition: $partitionOpt")
    val record: ProducerRecord[String, String] = (partitionOpt, keyOpt) match {
      case (Some(partition), Some(key)) => new ProducerRecord(topic, partition, key, value)
      case (None, Some(key)) => new ProducerRecord(topic, key, value)
      case _ => new ProducerRecord(topic, value)
    }
    producer.send(record)
  } catch {
    case e: Exception =>
      logger.error("Send record exception: ", e.getMessage)
      e.printStackTrace()
  }

}
