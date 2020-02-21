package com.example.catalogue.catalogueservice.service;

import com.example.catalogue.catalogueservice.messages.OrderMessage;

public interface RabbitOrderResponseService {
    void processOrderMessage(OrderMessage orderMessage);
}
