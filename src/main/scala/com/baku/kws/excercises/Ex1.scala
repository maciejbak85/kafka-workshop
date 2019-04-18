package com.baku.kws.excercises

import java.time.LocalDateTime

import akka.actor.{ActorSystem, Props}
import com.baku.kws.commons.ActorAcheduler
import com.baku.kws.commons.apache.{AConsumer, AProducer}

class Ex1(implicit system: ActorSystem) extends Exercise {

  val topic = "lesson_1s"
  val producer = new AProducer(topic)
  val consumer = new AConsumer("Consumer A", topic)

  override def run(): Unit = {
    system.actorOf(Props(new ActorAcheduler(() => producer.sendRecord("key_xyz", s"Value_${LocalDateTime.now()}"))))
    system.actorOf(Props(new ActorAcheduler(() => consumer.consume())))
  }

}
