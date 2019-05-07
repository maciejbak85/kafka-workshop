package com.baku.kws.excercises

import java.time.LocalDateTime

import akka.actor.{ActorSystem, Props}
import com.baku.kws.commons.ActorAcheduler
import com.baku.kws.commons.apache.{AConsumer, AProducer, CommonProps}
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.ProducerConfig

import scala.concurrent.duration._

class Ex10(implicit system: ActorSystem) extends Exercise with LazyLogging with CommonProps {

  val topic = "lesson_10"

  val customProps = kafkaProps
  customProps.put(ProducerConfig.ACKS_CONFIG, "0")

  val producers = (0 to 1).map { num =>
    (num, new AProducer(topic, partitionOpt = Some(num)))
  }
  val consumers = (0 to 1).map { num =>
    new AConsumer("Bussy Consumer number "+num, topic)
  }

  override def run(): Unit = {
    logger.info("Running exercise 10")

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
