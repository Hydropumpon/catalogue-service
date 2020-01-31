package com.example.catalogue.catalogueservice.listener;

import com.example.catalogue.catalogueservice.messages.OrderMessage;
import com.example.catalogue.catalogueservice.service.OrderCalcService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class OrderListener {

    private OrderCalcService orderCalcService;

    @Autowired
    public OrderListener(OrderCalcService orderCalcService) {
        this.orderCalcService = orderCalcService;
    }

    @RabbitListener(queues = "${rabbitmq.queues.order}")
    public void onOrderMessageReceive(OrderMessage orderMessage, Channel channel, @Header(
            AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        log.debug(orderMessage.toString());
        orderMessage = orderCalcService.calcOrder(orderMessage);
        log.debug(orderMessage.toString());
        channel.basicAck(tag, false);
    }
}
