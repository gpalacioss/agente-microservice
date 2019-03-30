package com.legosoft.cqrs.config;

import com.legosoft.cqrs.RabbitMQreceiver.ReceiverUsuario;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfig {

    public static final String EXCHANGE_NAME = "usuarioCQRS";
    public static final String ROUTING_KEY = "usuarioCQRS";

    private static final String QUEUE_NAME = "cqrs";
    private static final boolean IS_DURABLE_QUEUE = true;

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, IS_DURABLE_QUEUE);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    ReceiverUsuario receiver() {
        return new ReceiverUsuario();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ReceiverUsuario receiver) {
        return new MessageListenerAdapter(receiver, ReceiverUsuario.RECEIVE_METHOD_NAME);
    }
}


