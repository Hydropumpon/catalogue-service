package com.example.catalogue.catalogueservice.service.impl;

import com.example.catalogue.catalogueservice.entity.Item;
import com.example.catalogue.catalogueservice.messages.OrderMessage;
import com.example.catalogue.catalogueservice.messages.OrderStates;
import com.example.catalogue.catalogueservice.service.ItemService;
import com.example.catalogue.catalogueservice.service.OrderCalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderCalcServiceImpl implements OrderCalcService {

    private ItemService itemService;

    @Autowired
    public OrderCalcServiceImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    @Transactional
    public OrderMessage calcOrder(OrderMessage orderMessage) {

        orderMessage.setApproveDate(LocalDate.now());
        Map<Integer, Item> itemPrices = orderMessage.getOrderLineList()
                                                    .stream()
                                                    .map(orderLine -> itemService
                                                            .getItemsByIdAndQuantity(orderLine.getItemId(),
                                                                                     orderLine.getQuantity()))
                                                    .filter(Optional::isPresent)
                                                    .map(Optional::get)
                                                    .collect(Collectors.toMap(Item::getId, item -> item));

        if (itemPrices.size() != orderMessage.getOrderLineList().size()) {
            orderMessage.setAmount(BigDecimal.ZERO);
            orderMessage.setState(OrderStates.REJECTED);
            return orderMessage;
        }

        orderMessage.setAmount(orderMessage.getOrderLineList()
                                           .stream()
                                           .map(orderLine -> {
                                               itemPrices.get(orderLine.getItemId()).setQuantity(
                                                       itemPrices.get(orderLine.getItemId()).getQuantity() -
                                                               orderLine.getQuantity());
                                               return BigDecimal.valueOf(orderLine.getQuantity())
                                                                .multiply(itemPrices.get(orderLine.getItemId())
                                                                                    .getPrice());
                                           })
                                           .reduce(BigDecimal.ZERO, BigDecimal::add));

        orderMessage.setState(OrderStates.APPROVED);
        return orderMessage;
    }
}
