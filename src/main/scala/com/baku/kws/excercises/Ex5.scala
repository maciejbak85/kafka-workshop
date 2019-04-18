package com.baku.kws.excercises

import java.time.LocalDateTime

import akka.actor.{ActorSystem, Props}
import com.baku.kws.commons.ActorAcheduler
import com.baku.kws.commons.apache.{AConsumer, AProducer}
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.duration._

class Ex5(implicit system: ActorSystem) extends Exercise with LazyLogging {

  val topic = "lesson_5"
  val producer = new AProducer(topic)
  val consumer = new AConsumer("Consumer 2", topic, msgConsumingTime = 5.seconds)

  override def run(): Unit = {
    logger.info("Running exercise 5")
    system.actorOf(Props(new ActorAcheduler(() => {
      producer.sendRecord("less_5", s"Value_${LocalDateTime.now()}")
    }, interval = 500.milliseconds)))
    system.actorOf(Props(new ActorAcheduler(() => consumer.consume(), interval = 10.seconds)))
  }

}
