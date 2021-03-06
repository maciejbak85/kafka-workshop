package com.baku.kws.excercises

import java.time.LocalDateTime

import akka.actor.{ActorSystem, Props}
import com.baku.kws.commons.ActorAcheduler
import com.baku.kws.commons.apache.{AConsumer, AProducer}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration._

class Ex9(implicit system: ActorSystem) extends Exercise with LazyLogging  {

  val topic = "lesson_9"

  val producers = (0 to 1).map { num =>
    (num, new AProducer(topic, partitionOpt = Some(num)))
  }
  val consumers = (0 to 5).map { num =>
    new AConsumer("Bussy Consumer number "+num, topic)
  }

  override def run(): Unit = {
    logger.info("Running exercise 9")

    producers.foreach {
      case (num, producer) =>
        system.actorOf(Props(new ActorAcheduler(() => {
          producer.sendRecord("key_"+num, s"Value_${LocalDateTime.now()}")
        }, interval = 2.seconds)))
    }

    consumers.foreach { consumer =>
      system.actorOf(Props(new ActorAcheduler(() => consumer.consume())))
    }

  }

}
