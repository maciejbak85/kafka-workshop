package com.baku.kws.excercises

import java.time.LocalDateTime

import akka.actor.{ActorSystem, Props}
import com.baku.kws.commons.ActorAcheduler
import com.baku.kws.commons.apache.{AConsumer, AProducer}
import com.typesafe.scalalogging.LazyLogging

import scala.util.Random

class Ex4(implicit system: ActorSystem) extends Exercise with LazyLogging {

  val topic = "lesson_4"
  val producer = new AProducer(topic)
  val consumer = new AConsumer("Consumer 2", topic)

  override def run(): Unit = {
    logger.info("Running exercise 4")
    system.actorOf(Props(new ActorAcheduler(() => {
      producer.sendRecord(None, s"Value_${LocalDateTime.now()}")
    })))
    system.actorOf(Props(new ActorAcheduler(() => consumer.consume())))
  }

}
