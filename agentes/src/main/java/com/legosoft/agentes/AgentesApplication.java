package com.legosoft.agentes;

import org.axonframework.boot.autoconfig.AMQPAutoConfiguration;
import org.axonframework.boot.autoconfig.AxonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableAutoConfiguration(exclude = AxonAutoConfiguration.class)
@EnableAutoConfiguration(exclude={AxonAutoConfiguration.class, AMQPAutoConfiguration.class})
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class AgentesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentesApplication.class, args);
    }
}
