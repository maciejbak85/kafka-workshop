##
Docker repo for environment:
https://github.com/simplesteph/kafka-stack-docker-compose

## Ex 1
1 topic, 1 partition
Producer send to topic, consumer consumes message

## Ex 2
Set partition number for producer

## Ex 3 
Set key without partition
partition should be based on key hash

## Ex 4
no key, no partition
topic created with few partitions
should be round robin

## Ex 5
Consumer slower than producer
What will happen ?

## Ex 6
custom partitioner

## Exercises with customer group and partitions number
1 topic with multiple partitions, consumer group
<https://kafka.apache.org/documentation/#basic_ops_consumer_group>

## Ex 7
-  Consumers < partitions

## Ex 8
-  Consumers == partitions

## Ex 9
-  Consumers > partitions

## Ex 10
acks = 0

## Producer acks configs 
acks = 0
acks = 1 ( sends by a leader )
acks = all

## Producer retries configs 

## Producer max.in.flight.requests.per.connection configs 
For critical order use sync or connection = 1

## Consumer position
<https://kafka.apache.org/documentation/#basic_ops_consumer_lag>
consumer goes down, producer keeps pushing, then consumer back online

## DSL

<https://kafka.apache.org/22/documentation/streams/developer-guide/dsl-api.html#scala-dsl>

## schema evolution

## Replication factor

## kafka node go offline

## kafka node back online

## zookeeper go offline

## zookeeper go online

## use custom serializer ( extends Serializer )

## use avro serializer

## use Connect API

## use AdminClient

## Kafka config

## SSL

## Quota configuration

## metrics

## max.in.flight.requests.per.connection

## Stream processing
https://medium.com/high-alpha/data-stream-processing-for-newbies-with-kafka-ksql-and-postgres-c30309cfaaf8