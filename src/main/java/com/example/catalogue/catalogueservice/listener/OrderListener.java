package com.example.catalogue.catalogueservice.listener;

import com.example.catalogue.catalogueservice.client.OrderClient;
import com.example.catalogue.catalogueservice.messages.OrderMessage;
import com.example.catalogue.catalogueservice.service.OrderCalcService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Component
@Slf4j
public class OrderListener {

    private OrderCalcService orderCalcService;

    private OrderClient orderClient;

    @Autowired
    public OrderListener(OrderCalcService orderCalcService,
                         OrderClient orderClient) {
        this.orderCalcService = orderCalcService;
        this.orderClient = orderClient;
    }


    @RabbitListener(queues = "${rabbitmq.queues.order}")
    @Transactional
    public void onOrderMessageReceive(OrderMessage orderMessage, Channel channel, @Header(
            AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.debug(orderMessage.toString());
        orderMessage = orderCalcService.calcOrder(orderMessage);
        log.debug(orderMessage.toString());
        orderClient.updateOrder(orderMessage, orderMessage.getId());

    }
}
