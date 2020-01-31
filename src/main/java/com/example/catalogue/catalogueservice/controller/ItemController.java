package com.example.catalogue.catalogueservice.controller;

import com.example.catalogue.catalogueservice.converter.ConverterDto;
import com.example.catalogue.catalogueservice.dto.ItemDto;
import com.example.catalogue.catalogueservice.dto.wrapper.ItemDtoWrapper;
import com.example.catalogue.catalogueservice.entity.Item;
import com.example.catalogue.catalogueservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    private ItemService itemService;

    private ConverterDto<Item, ItemDto> itemConverterDto;

    @Autowired
    public ItemController(ItemService itemService,
                          ConverterDto<Item, ItemDto> itemConverterDto) {
        this.itemService = itemService;
        this.itemConverterDto = itemConverterDto;
    }

    @GetMapping
    public List<ItemDto> getItems(Pageable pageable) {
        return itemConverterDto.toDto(itemService.getItems(pageable).getContent());
    }

    @PostMapping
    public ItemDto addItem(@RequestBody @Valid ItemDtoWrapper dtoWrapper) {
        ItemDto itemDto = dtoWrapper.getItemDto();
        Item item = itemConverterDto.toEntity(itemDto);
        return itemConverterDto.toDto(itemService.addItem(item, dtoWrapper.getCategoryIds()));
    }

    @PutMapping
    public ItemDto updateItem(@RequestBody @Valid ItemDtoWrapper dtoWrapper) {
        ItemDto itemDto = dtoWrapper.getItemDto();
        Item item = itemConverterDto.toEntity(itemDto);
        return itemConverterDto.toDto(itemService.updateItem(item, dtoWrapper.getCategoryIds()));
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable("id") Integer id) {
        return itemConverterDto.toDto(itemService.getItemById(id));
    }

    @GetMapping("/filter/")
    public List<ItemDto> getByPriceLessAndManufacturer(@RequestParam("price") BigDecimal price,
                                                       @RequestParam("manufacturer") String manufacturer) {
        return itemConverterDto.toDto(itemService.getItemsByPriceLessAndManufacturer(price, manufacturer));
    }

    @GetMapping("/byCategory")
    public List<ItemDto> getByCategory(@RequestParam("category") String categoryName) {
        return itemConverterDto.toDto(itemService.getByCategory(categoryName));
    }

    @GetMapping("/byCategoryAndManufacturer")
    public List<ItemDto> getByCategoryAndManufacturer(@RequestParam("category") String categoryName,
                                                      @RequestParam("manufacturer") String manufacturerName) {
        return itemConverterDto.toDto(itemService.getByCategoryAndManufacturer(categoryName, manufacturerName));
    }

}
