package com.example.catalogue.catalogueservice.service.impl;

import com.example.catalogue.catalogueservice.messages.OrderLine;
import com.example.catalogue.catalogueservice.messages.OrderMessage;
import com.example.catalogue.catalogueservice.messages.OrderStates;
import com.example.catalogue.catalogueservice.service.ItemService;
import com.example.catalogue.catalogueservice.service.OrderCalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderCalcServiceImpl implements OrderCalcService {

    private ItemService itemService;

    @Autowired
    public OrderCalcServiceImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public OrderMessage calcOrder(OrderMessage orderMessage) {

        List<Integer> itemIds = orderMessage.getOrderLineList()
                                            .stream()
                                            .map(OrderLine::getItemId)
                                            .collect(Collectors.toList());

        List<BigDecimal> prices = itemService.findByIdInOrdered(itemIds);

        orderMessage.setApproveDate(LocalDate.now());

        if (prices.size() != itemIds.size()) {
            orderMessage.setState(OrderStates.REJECTED);
            return orderMessage;
        }

        BigDecimal amount = getTotalPrice(orderMessage, prices);

        orderMessage.setState(OrderStates.APPROVED);
        orderMessage.setAmount(amount);
        return orderMessage;

//        Function<OrderLine, BigDecimal> totalMapper =
//                orderLine -> itemService.getItemById(orderLine.getItemId())
//                                        .getPrice()
//                                        .multiply(BigDecimal.valueOf(
//                                                orderLine.getQuantity()));
//        todo подумать как реализовать запрос
//        orderMessage.setApproveDate(LocalDate.now());
//        try {
//            orderMessage.setAmount(
//                    orderMessage.getOrderLineList()
//                                .stream()
//                                .map(totalMapper)
//                                .reduce(BigDecimal.ZERO, BigDecimal::add));
//            orderMessage.setState(OrderStates.APPROVED);
//            return orderMessage;
//        } catch (NotFoundException e) {
//            orderMessage.setState(OrderStates.REJECTED);
//            return orderMessage;
//        }


    }

    private BigDecimal getTotalPrice(OrderMessage orderMessage, List<BigDecimal> prices) {
        BigDecimal amount = BigDecimal.ZERO;
        for (int i = 0; i < prices.size(); i++) {
            BigDecimal partialSum =
                    prices.get(i).multiply(BigDecimal.valueOf(orderMessage.getOrderLineList().get(i).getQuantity()));
            amount = amount.add(partialSum);
        }
        return amount;
    }
}
