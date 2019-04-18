package com.baku.kws.excercises

import java.time.LocalDateTime

import akka.actor.{ActorSystem, Props}
import com.baku.kws.commons.ActorAcheduler
import com.baku.kws.commons.apache.{AConsumer, AProducer}
import com.typesafe.scalalogging.LazyLogging

class Ex2(implicit system: ActorSystem) extends Exercise with LazyLogging {

  val topic = "lesson_2"
  val producer = new AProducer(topic, partitionOpt = Some(1))
  val consumer = new AConsumer("Consumer 2", topic)

  override def run(): Unit = {
    logger.info("Running exercise 2")
    system.actorOf(Props(new ActorAcheduler(() => {
      logger.info("TICK PRODUCER")
      producer.sendRecord("key_xyz", s"Value_${LocalDateTime.now()}")
    })))
    system.actorOf(Props(new ActorAcheduler(() => consumer.consume())))
  }

}
