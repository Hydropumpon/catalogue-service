package com.example.catalogue.catalogueservice.service.impl;

import com.example.catalogue.catalogueservice.messages.OrderMessage;
import com.example.catalogue.catalogueservice.service.RabbitOrderResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitOrderResponseServiceImpl implements RabbitOrderResponseService {

    @Value("${rabbitmq.exchanges.order_response}")
    private String orderResponseExchange;

    @Value("${rabbitmq.routing_key.order_response}")
    private String orderResponseRoutingKey;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitOrderResponseServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void processOrderMessage(OrderMessage orderMessage) {
        rabbitTemplate.convertAndSend(orderResponseExchange, orderResponseRoutingKey, orderMessage);
    }
}
