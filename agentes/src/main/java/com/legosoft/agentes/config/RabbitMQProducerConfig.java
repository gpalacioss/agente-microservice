package com.legosoft.agentes.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQProducerConfig {

    @Value("${axon.amqp.exchange}")
    String exchangeName;

    @Value("${axon.amqp.routingkey}")
    String routingKey;

    @Value("${axon.amqp.queuename}")
    String queueName;

    @Value("${axon.amqp.exchange}")
    String exchangeNameCqrs;

    @Value("${axon.amqp.routingkey}")
    String routingKeyCqrs;

    @Value("${axon.amqp.queuename}")
    String queueNameCqrs;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("192.168.1.66");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setPort(5673);
        return connectionFactory;
    }

    /**
     * Configuracion para envio de cola al servcio de usuario
     * @return
     */

    @Bean
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(exchangeName).build();
    }

    @Bean
    public Queue queue(){
        return new Queue(queueName, true);
    }

    @Bean
    public Binding binding(){
        return new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName,"*", null);
    }

    @Bean
    @Required
    public RabbitAdmin rabbitAdmin() {

        RabbitAdmin admin = new RabbitAdmin(connectionFactory());
        admin.setAutoStartup(true);
        admin.declareExchange(exchange());
        admin.declareQueue(queue());
        admin.declareBinding(binding());
        return admin;
    }

    /**
     * Configuracion para el envio de cola al cqrs
     * @return
     */
    @Bean
    public Exchange exchangeCqrs(){
        return ExchangeBuilder.topicExchange(exchangeNameCqrs).build();
    }

    @Bean
    public Queue queueCqrs(){
        return new Queue(queueNameCqrs, true);
    }

    @Bean
    public Binding bindingCqrs(){
        return new Binding(queueName, Binding.DestinationType.QUEUE, exchangeNameCqrs,"*", null);
    }

    @Bean
    @Required
    public RabbitAdmin rabbitAdminCqrs() {

        RabbitAdmin admin = new RabbitAdmin(connectionFactory());
        admin.setAutoStartup(true);
        admin.declareExchange(exchangeCqrs());
        admin.declareQueue(queueCqrs());
        admin.declareBinding(bindingCqrs());
        return admin;
    }


    @Bean
    RabbitTransactionManager transactionManager(){
        RabbitTransactionManager txMgr = new RabbitTransactionManager(connectionFactory());
        return txMgr;
    }
}
