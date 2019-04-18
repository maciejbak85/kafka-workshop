package com.baku.kws.commons.apache

import java.util.Properties

trait CommonProps {

  val kafkaProps = new Properties()
  val kafkaNodes = 3
  (1 to kafkaNodes).foreach { num =>
    kafkaProps.put("bootstrap.servers", "localhost:909"+num)
  }

  kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

  kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

}
