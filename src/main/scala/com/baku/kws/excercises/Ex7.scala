package com.baku.kws.excercises

import java.time.LocalDateTime

import akka.actor.{ActorSystem, Props}
import com.baku.kws.commons.ActorAcheduler
import com.baku.kws.commons.apache.{AConsumer, AProducer, CommonProps}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration._
import scala.util.Random

class Ex7(implicit system: ActorSystem) extends Exercise with LazyLogging {

  val topic = "lesson_7"

  val producers = (0 to 4).map { num =>
    (num, new AProducer(topic, partitionOpt = Some(num)))
  }
  val consumer = new AConsumer("Bussy Consumer", topic, msgConsumingTime = 5.seconds)

  override def run(): Unit = {
    logger.info("Running exercise 7")

    producers.foreach { numProd =>
      val num = numProd._1
      val producer = numProd._2

      system.actorOf(Props(new ActorAcheduler(() => {
        producer.sendRecord("key_"+num, s"Value_${LocalDateTime.now()}")
      }, interval = 5.seconds)))
    }
    system.actorOf(Props(new ActorAcheduler(() => consumer.consume())))
  }

}
