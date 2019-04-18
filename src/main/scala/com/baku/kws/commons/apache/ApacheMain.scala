package com.baku.kws.commons.apache

import java.time.{LocalDateTime, ZoneOffset}

import scala.concurrent.Future
import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global

object ApacheMain extends App {

  val topic = "apache_topic"
  val producer = new AProducer(topic)
  val consumer = new AConsumer("aaa", topic)

  def startProducingRecords(): Future[Unit] = Future {
    (1 to 5).foreach { _ =>
      val key = "produced_"+Random.nextInt(1000)
      println("PRODUCED key: "+key)
      producer.sendRecord(key, LocalDateTime.now(ZoneOffset.UTC).toString)
      Thread.sleep(1000)
    }
  }

  startProducingRecords()

  consumer.consume

  Thread.sleep(15000)

}
