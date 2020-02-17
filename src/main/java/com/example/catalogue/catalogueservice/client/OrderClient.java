package com.example.catalogue.catalogueservice.client;

import com.example.catalogue.catalogueservice.messages.OrderMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "${client.order-service.name}")
public interface OrderClient {

    @PutMapping("/order-service/order/{id}")
    public void updateOrder(OrderMessage orderMessage, @PathVariable("id") Integer id);
}
