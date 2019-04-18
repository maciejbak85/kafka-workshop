package com.baku.kws.excercises

import java.time.LocalDateTime

import akka.actor.{ActorSystem, Props}
import com.baku.kws.commons.ActorAcheduler
import com.baku.kws.commons.apache.{AConsumer, AProducer, CommonProps}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration._
import scala.util.Random

class Ex6(implicit system: ActorSystem) extends Exercise with LazyLogging with CommonProps {

  val topic = "lesson_6"

  val props = kafkaProps
  props.put("partitioner.class", "com.baku.kws.excercises.MyCustomPartitioner")
  props.put("sensor.name", "XYZ")

  val producer = new AProducer(topic, customProps = Some(props))
  val consumer = new AConsumer("Consumer 2", topic, msgConsumingTime = 5.seconds)

  override def run(): Unit = {
    logger.info("Running exercise 6")
    system.actorOf(Props(new ActorAcheduler(() => {
      val key = if (Random.nextInt(3) == 2) "XYZ" else "ABC"
      producer.sendRecord(key, s"Value_${LocalDateTime.now()}")
    }, interval = 500.milliseconds)))
    system.actorOf(Props(new ActorAcheduler(() => consumer.consume(), interval = 10.seconds)))
  }

}
