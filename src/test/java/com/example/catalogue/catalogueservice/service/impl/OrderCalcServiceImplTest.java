package com.example.catalogue.catalogueservice.service.impl;

import com.example.catalogue.catalogueservice.repository.ItemRepository;
import com.example.catalogue.catalogueservice.service.OrderCalcService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderCalcServiceImplTest {

    @Mock
    private ItemRepository itemService;

    @InjectMocks
    private OrderCalcService orderCalcService;

    @Test
    public void calcOrder() {

    }
}