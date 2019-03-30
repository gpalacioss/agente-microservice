package com.legosoft.facultades.circuitbreaker;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.Future;

@Slf4j
@Service("consumerRestCircuitBreaker")
public class ConsumerRestCircuitBreaker {

    private final String URL_SERVICE = "http://192.168.20.93:8081/message/restore/create/";
//    private final String URL_SERVICE = "http://message-restore/message/restore/create/";


    @LoadBalanced
    private RestTemplate restTemplate = new RestTemplate();

//    //TODO: Este metodo realiza un control de ruptura del circuito de comunicacion de modo sincrono
////    @HystrixCommand(fallbackMethod = "errorHystrix")
//    public String consumerRestRabbitService(Map<String, Object> info) {
////        restTemplate.exchange(URL_SERVICE, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, "abcschool");
//        return  restTemplate.postForObject(URL_SERVICE, info, String.class);
//    }

    //    TODO: Este metodo realiza un control de ruptura del circuito de comunicacion de modo asincrono
    @HystrixCommand(fallbackMethod = "errorHystrix")
    public Future<String> consumerRestRabbitService(Map<String, Object> info) {
        return new AsyncResult<String>() {
            public String invoke() {
                return restTemplate.postForObject(URL_SERVICE, info, String.class);
            }
        };
    }

    public String errorHystrix(Map<String, Object> info){
        log.info("<<<<<<<<<<< El rest esta fallando >>>>>>>>>>>>>>>>>");
        return "el servicio esta fallando";
    }

}
