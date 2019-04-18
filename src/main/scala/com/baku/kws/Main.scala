package com.baku.kws

import akka.actor.ActorSystem
import com.baku.kws.excercises._
import com.typesafe.scalalogging.LazyLogging

object Main extends App with LazyLogging {

  implicit val system = ActorSystem("a")

  logger.info("Choose exercise")
  val exNum = scala.io.StdIn.readInt()
  logger.info("You choosed "+exNum)

  val ex = exNum match {
    case 1 => new Ex1()
    case 2 => new Ex2()
    case 3 => new Ex3()
    case 4 => new Ex4()
    case 5 => new Ex5()
    case 6 => new Ex6()
    case 7 => new Ex7()
    case 8 => new Ex8()
    case 9 => new Ex9()
    case _ => throw new Exception("Invalid number provided.")
  }

  ex.run()

}
