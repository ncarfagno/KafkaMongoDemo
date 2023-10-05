package com.ncarfagno.kafkaMongoDemo.config;

import com.ncarfagno.kafkaMongoDemo.domain.ProcessedEvent;
import com.ncarfagno.kafkaMongoDemo.repository.ProcessedEventRepository;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.kafka.streams.StreamsConfig;
import java.util.Properties;

@Configuration
public class KafkaConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    @Autowired
    private ProcessedEventRepository repository;

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public KafkaStreams kafkaStreams() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        
        for (String topic : kafkaProperties.getTopics()) {
            KStream<String, String> stream = builder.stream(topic);
            
            stream.foreach((key, value) -> {
                logger.info("Received event with key: {} and value: {}", key, value);
                ProcessedEvent event = new ProcessedEvent();
                event.setContent(value);
                repository.save(event);
            });
        }
        
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
        
        return streams;
    }
}
