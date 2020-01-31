package com.example.catalogue.catalogueservice.service;


import com.example.catalogue.catalogueservice.messages.OrderMessage;

public interface OrderCalcService {

    OrderMessage calcOrder(OrderMessage orderMessage);

}
