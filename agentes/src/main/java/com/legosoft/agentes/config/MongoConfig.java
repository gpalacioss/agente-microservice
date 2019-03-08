//package com.legosoft.agentes.config;
//
//import com.legosoft.agentes.sagas.CreateAgenteSaga;
//import com.mongodb.MongoClient;
//import com.mongodb.ServerAddress;
//import org.axonframework.eventhandling.saga.AbstractSagaManager;
//import org.axonframework.eventhandling.saga.AnnotatedSagaManager;
//import org.axonframework.eventhandling.saga.ResourceInjector;
//import org.axonframework.eventhandling.saga.SagaRepository;
//import org.axonframework.eventhandling.saga.repository.AnnotatedSagaRepository;
//import org.axonframework.eventhandling.saga.repository.SagaStore;
//import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
//import org.axonframework.mongo.DefaultMongoTemplate;
//import org.axonframework.mongo.MongoTemplate;
//import org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore;
//import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
//import org.axonframework.mongo.eventsourcing.eventstore.MongoFactory;
//import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
//import org.axonframework.serialization.Serializer;
//import org.axonframework.serialization.json.JacksonSerializer;
//import org.axonframework.spring.saga.SpringResourceInjector;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Collections;
//
//@Configuration
//public class MongoConfig {
//
//    @Value("${spring.data.mongodb.host}")
//    private String mongoHost;
//
//    @Value("${spring.data.mongodb.port}")
//    private int mongoPort;
//
//    @Value("${spring.data.mongodb.database}")
//    private String mongodbName;
//
//    @Bean
//    Serializer axonJsonSerializer(){
//        return new JacksonSerializer();
//    }
//
//    @Bean
//    public EventStorageEngine eventStore(){
//        return new MongoEventStorageEngine(
//                axonJsonSerializer(),null, axonMongoTemplate(), new DocumentPerEventStorageStrategy());
//    }
//
//    @Bean
//    public MongoTemplate axonMongoTemplate(){
//        return new DefaultMongoTemplate(mongoClient(),mongodbName);
//    }
//
//    @Bean
//    public MongoClient mongoClient(){
//        MongoFactory mongoFactory = new MongoFactory();
//        mongoFactory.setMongoAddresses(Collections.singletonList(new ServerAddress(mongoHost,mongoPort)));
//        return mongoFactory.createMongo();
//    }
//


//}
