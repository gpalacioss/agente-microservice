//package com.legosoft.agentes.config;
//
//import com.legosoft.agentes.RabbitMQReceiver.Receiver;
//import com.legosoft.agentes.RabbitMQReceiver.ReceiverGrupo;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConsumerConfig {
//
//    private static final boolean IS_DURABLE_QUEUE = false;
//
//    public static final String EXCHANGE_NAME = "usuarioNuevo";
//    public static final String ROUTING_KEY = "usuarioNuevo";
//    private static final String QUEUE_NAME = "usuario_nuevo";
//
//
//    public static final String EXCHANGE_NAME_GRUPO = "grupo_agente";
//    public static final String ROUTING_KEY_GRUPO = "grupo_agente";
//    private static final String QUEUE_NAME_GRUPO = "grupo_agente";
//
//
//    @Bean
//    Queue queue() {
//        return new Queue(QUEUE_NAME, IS_DURABLE_QUEUE);
//    }
//
//    @Bean
//    TopicExchange exchange() {
//        return new TopicExchange(EXCHANGE_NAME);
//    }
//
//    @Bean
//    Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
//    }
//
//    /**
//     * Cosnumer del grupo
//     * @return
//     */
//    @Bean
//    Queue queueGrupoUsuario() {
//        return new Queue(QUEUE_NAME_GRUPO, IS_DURABLE_QUEUE);
//    }
//
//    @Bean
//    TopicExchange exchangeGrupoUsuario() {
//        return new TopicExchange(EXCHANGE_NAME_GRUPO);
//    }
//
//    @Bean
//    Binding bindingGrupoUsuario(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_GRUPO);
//    }
//
//
//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
//        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(QUEUE_NAME);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
//
//    @Bean
//    SimpleMessageListenerContainer containerGrupo(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapterGrupo) {
//        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(QUEUE_NAME_GRUPO);
//        container.setMessageListener(listenerAdapterGrupo);
//        return container;
//    }
//
//    @Bean
//    Receiver receiver() {
//        return new Receiver();
//    }
//
//    @Bean
//    ReceiverGrupo receiverGrupo() {
//        return new ReceiverGrupo();
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver) {
//        return new MessageListenerAdapter(receiver, Receiver.RECEIVE_METHOD_NAME);
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapterGrupo(ReceiverGrupo receiverGrupo) {
//        return new MessageListenerAdapter(receiverGrupo, ReceiverGrupo.RECEIVE_METHOD_NAME_GRUPO);
//    }
//}
//
//
