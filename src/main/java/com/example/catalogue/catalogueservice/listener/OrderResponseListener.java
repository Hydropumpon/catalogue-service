package com.example.catalogue.catalogueservice.listener;

import com.example.catalogue.catalogueservice.client.OrderClient;
import com.example.catalogue.catalogueservice.messages.OrderMessage;
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
public class OrderResponseListener {

    private OrderClient orderClient;

    @Autowired
    public OrderResponseListener(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @RabbitListener(queues = "${rabbitmq.queues.order_response}")
    @Transactional
    public void onOrderMessageReceive(OrderMessage orderMessage, Channel channel, @Header(
            AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.debug(orderMessage.toString());
        orderClient.updateOrder(orderMessage, orderMessage.getId());
        log.debug(orderMessage.toString());
    }
}
