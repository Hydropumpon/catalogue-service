package com.example.catalogue.catalogueservice.controller;

import com.example.catalogue.catalogueservice.converter.ConverterDto;
import com.example.catalogue.catalogueservice.dto.ItemDto;
import com.example.catalogue.catalogueservice.dto.wrapper.ItemDtoWrapper;
import com.example.catalogue.catalogueservice.entity.Item;
import com.example.catalogue.catalogueservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("catalogue/item")
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

    @PutMapping("/{id}")
    public ItemDto updateItem(@RequestBody @Valid ItemDtoWrapper dtoWrapper, @PathVariable("id") Integer id) {
        ItemDto itemDto = dtoWrapper.getItemDto();
        Item item = itemConverterDto.toEntity(itemDto);
        return itemConverterDto.toDto(itemService.updateItem(item, dtoWrapper.getCategoryIds(), id));
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable("id") Integer id) {
        return itemConverterDto.toDto(itemService.getItemById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable("id") Integer id) {
        itemService.deleteItem(id);
    }

    @GetMapping("/byCategory")
    public List<ItemDto> getByCategory(@RequestParam("category") String categoryName) {
        return itemConverterDto.toDto(itemService.getByCategory(categoryName));
    }

    @GetMapping("/byCategoryAndManufacturer")
    public List<ItemDto> getByCategoryAndManufacturer(@RequestParam(name = "category") String categoryName,
                                                      @RequestParam(name = "manufacturer") String manufacturerName) {
        return itemConverterDto.toDto(itemService.getByCategoryAndManufacturer(categoryName, manufacturerName));
    }

}
