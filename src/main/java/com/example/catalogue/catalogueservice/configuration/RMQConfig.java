package com.example.catalogue.catalogueservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
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

@Configuration
@EnableRabbit
public class RMQConfig {

    @Value("${rabbitmq.queues.order}")
    private String orderQueue;

    @Value("${rabbitmq.exchanges.order")
    private String orderExchange;

    private RabbitTemplate rabbitTemplate;

    private MessageConverter messageConverter;

    private SimpleRabbitListenerContainerFactory containerFactory;

    private RabbitAdmin rabbitAdmin;

    @Autowired
    public RMQConfig(RabbitTemplate rabbitTemplate,
                     MessageConverter messageConverter,
                     SimpleRabbitListenerContainerFactory containerFactory,
                     RabbitAdmin rabbitAdmin) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = messageConverter;
        this.containerFactory = containerFactory;
        this.rabbitAdmin = rabbitAdmin;
    }


    @PostConstruct
    protected void init() {
        rabbitTemplate.setMessageConverter(messageConverter);
        containerFactory.setMessageConverter(messageConverter);
        containerFactory.setMissingQueuesFatal(false);

        Queue queue = new Queue(orderQueue);
        DirectExchange exchange = new DirectExchange(orderExchange);
        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), "", null);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(binding);

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
