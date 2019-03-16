package com.legosoft.facultades;

import org.axonframework.boot.autoconfig.AMQPAutoConfiguration;
import org.axonframework.boot.autoconfig.AxonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableAutoConfiguration(exclude={AMQPAutoConfiguration.class})
@SpringBootApplication
@EnableEurekaClient
public class FacultadesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacultadesApplication.class, args);
	}

}
