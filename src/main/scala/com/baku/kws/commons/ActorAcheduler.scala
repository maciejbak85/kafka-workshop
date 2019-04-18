package com.baku.kws.commons

import akka.actor.Actor

import scala.concurrent.duration._

case object Tick

class ActorAcheduler(runOnTick: () => Unit, interval: FiniteDuration = 5.seconds) extends Actor {
  import context.{dispatcher, system}

  override def receive: Receive = {
    case Tick => runOnTick()
  }

  system.scheduler.schedule(1 second, interval, self, Tick)
}
