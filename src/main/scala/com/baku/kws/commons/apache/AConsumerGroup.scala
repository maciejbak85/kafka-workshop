package com.baku.kws.commons.apache

import java.time.{Duration => JDuration}
import java.util.Collections

import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.collection.JavaConverters._
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

class AConsumerGroup(topic: String) extends CommonProps {

  kafkaProps.put("group.id", "apache_group")
  private val consumer = new KafkaConsumer[String, String](kafkaProps)
  consumer.subscribe(Collections.singletonList(topic))

  def consume(implicit ec: ExecutionContext): Future[Unit] = Future {
   while(true) {
     try {
       //println("Consumming....")
       val dur = JDuration.ofNanos(1.second.toNanos)
       val records = consumer.poll(dur).iterator().asScala.toSeq
       //println("records: "+records)
       records.foreach({ record =>
         val key = record.key()
         val value = record.value()
         println(s"CONSUMED key: $key value: $value")
         consumer.commitAsync()
       })

     } catch {
       case e: Exception =>
         e.printStackTrace()
     }
     //Thread.sleep(500)
   }
  }

  def close() = consumer.close()

}
