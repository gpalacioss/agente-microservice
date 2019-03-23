package com.legosoft.agentes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableAutoConfiguration(exclude = AMQPAutoConfiguration.class)
//@EnableAutoConfiguration(exclude={AxonAutoConfiguration.class, AMQPAutoConfiguration.class})
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class AgentesApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AgentesApplication.class, args);
    }
}
