package com.example.catalogue.catalogueservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RMQConfig {

    private static final String DEAD_QUEUE_EXCHANGE_PARAM_NAME = "x-dead-letter-exchange";
    private static final String DEAD_QUEUE_ROUTING_KEY_PARAM_NAME = "x-dead-letter-routing-key";
    private static final String QUEUE_TTL_PARAM_NAME = "x-message-ttl";

    @Value("${rabbitmq.dead_order_request.ttl}")
    private Long deadOrderRequestTtl;

    @Value("${rabbitmq.routing_key.order_request}")
    private String orderRequestRoutingKey;

    @Value("${rabbitmq.routing_key.dead_order_request}")
    private String deadOrderRequestRoutingKey;

    @Value("${rabbitmq.queues.order_request}")
    private String orderRequestQueue;

    @Value("${rabbitmq.exchanges.order_request}")
    private String orderRequestExchange;

    @Value("${rabbitmq.queues.dead_order_request}")
    private String deadOrderRequestQueue;

    @Value("${rabbitmq.exchanges.dead_order_request}")
    private String deadOrderRequestExchange;

    @Value("${rabbitmq.dead_order_response.ttl}")
    private Long deadOrderResponseTtl;

    @Value("${rabbitmq.routing_key.order_response}")
    private String orderResponseRoutingKey;

    @Value("${rabbitmq.routing_key.dead_order_response}")
    private String deadOrderResponseRoutingKey;

    @Value("${rabbitmq.queues.order_response}")
    private String orderResponseQueue;

    @Value("${rabbitmq.exchanges.order_response}")
    private String orderResponseExchange;

    @Value("${rabbitmq.queues.dead_order_response}")
    private String deadOrderResponseQueue;

    @Value("${rabbitmq.exchanges.dead_order_response}")
    private String deadOrderResponseExchange;



    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private SimpleRabbitListenerContainerFactory containerFactory;

    @Autowired
    private RabbitAdmin rabbitAdmin;


    @PostConstruct
    protected void init() {
        rabbitTemplate.setMessageConverter(messageConverter);
        containerFactory.setMessageConverter(messageConverter);
        containerFactory.setMissingQueuesFatal(false);
        declareOrderRequestQueue();
        declareOrderResponseQueue();
    }

    private void declareOrderResponseQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put(DEAD_QUEUE_EXCHANGE_PARAM_NAME, orderResponseExchange);
        args.put(DEAD_QUEUE_ROUTING_KEY_PARAM_NAME, orderResponseQueue);
        args.put(QUEUE_TTL_PARAM_NAME, deadOrderResponseTtl);

        rabbitAdmin.declareQueue(new Queue(deadOrderResponseQueue, true, false, false, args));
        rabbitAdmin.declareExchange(new DirectExchange(deadOrderResponseExchange, true, false));
        rabbitAdmin.declareBinding(
                new Binding(deadOrderResponseQueue, Binding.DestinationType.QUEUE, deadOrderResponseExchange,
                            deadOrderResponseRoutingKey,
                            null));

        args = new HashMap<>();
        args.put(DEAD_QUEUE_EXCHANGE_PARAM_NAME, deadOrderResponseExchange);
        args.put(DEAD_QUEUE_ROUTING_KEY_PARAM_NAME, deadOrderResponseRoutingKey);

        rabbitAdmin.declareQueue(new Queue(orderResponseQueue, true, false, false, args));
        rabbitAdmin.declareExchange(new DirectExchange(orderResponseExchange, true, false));
        rabbitAdmin.declareBinding(
                new Binding(orderResponseQueue, Binding.DestinationType.QUEUE, orderResponseExchange, orderResponseRoutingKey, null));
    }


    private void declareOrderRequestQueue() {

        Map<String, Object> args = new HashMap<>();
        args.put(DEAD_QUEUE_EXCHANGE_PARAM_NAME, orderRequestExchange);
        args.put(DEAD_QUEUE_ROUTING_KEY_PARAM_NAME, orderRequestQueue);
        args.put(QUEUE_TTL_PARAM_NAME, deadOrderRequestTtl);

        rabbitAdmin.declareQueue(new Queue(deadOrderRequestQueue, true, false, false, args));
        rabbitAdmin.declareExchange(new DirectExchange(deadOrderRequestExchange, true, false));
        rabbitAdmin.declareBinding(
                new Binding(deadOrderRequestQueue, Binding.DestinationType.QUEUE, deadOrderRequestExchange,
                            deadOrderRequestRoutingKey,
                            null));

        args = new HashMap<>();
        args.put(DEAD_QUEUE_EXCHANGE_PARAM_NAME, deadOrderRequestExchange);
        args.put(DEAD_QUEUE_ROUTING_KEY_PARAM_NAME, deadOrderRequestRoutingKey);

        rabbitAdmin.declareQueue(new Queue(orderRequestQueue, true, false, false, args));
        rabbitAdmin.declareExchange(new DirectExchange(orderRequestExchange, true, false));
        rabbitAdmin.declareBinding(
                new Binding(orderRequestQueue, Binding.DestinationType.QUEUE, orderRequestExchange, orderRequestRoutingKey, null));
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper()
                                                        .registerModule(new JavaTimeModule())
                                                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                                                                   true));
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(rabbitTemplate);
    }
}
