package com.legosoft.agentes;

import com.legosoft.agentes.circuitbreaker.ConsumerRestCircuitBreaker;
import com.legosoft.agentes.model.Compania;
import com.legosoft.agentes.service.CompaniaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AgentesApplicationTests {

    @Autowired
    private CompaniaService companiaService;

    @Autowired
    private ConsumerRestCircuitBreaker consumerRestCircuitBreaker;

    @Test
    public void contextLoads() {

        companiaService.findCompaniaById(1L);
    }

    @Test
    public void hystrix(){
        Compania compania = new Compania();

//        consumerRestCircuitBreaker.consumerRestRabbitService(compania);
    }

}
