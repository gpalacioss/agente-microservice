package com.legosoft.agentes.circuitbreaker;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConsumerRestCircuitBreaker {


    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "errorHystrix")
    public String consumerRestRabbitService() {
        String datos = "";
        return restTemplate.getForObject("http:192.168.20.109/rest", String.class, datos);
    }

    public String errorHystrix(){
        return "el servicio esta fallando";
    }
}
