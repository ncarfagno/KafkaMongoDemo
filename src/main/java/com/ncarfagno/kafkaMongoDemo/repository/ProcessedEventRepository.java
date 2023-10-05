package com.ncarfagno.kafkaMongoDemo.repository;


import com.ncarfagno.kafkaMongoDemo.domain.ProcessedEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProcessedEventRepository extends MongoRepository<ProcessedEvent, String> {
}
