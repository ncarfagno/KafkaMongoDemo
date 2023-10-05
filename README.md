# KafkaMongoDemo
Demo repo for learning Spring Boot utilizing Kafka and Mongo

To start the stack, do the following:

1. Run "docker-compose up" (add -d if you want to run in detatched mode)
    1a. This will start dockerized versions of Zookeeper and the Kafka Broker, as well as a dockerized MongoDB
2. Start the spring boot java app however you like.
   2a. I just push play in IntelliJ

There are multiple ways to publish messages over kafka. If you prefer a GUI approach, Conduktor (https://www.conduktor.io/) works well.