package com.example.catalogue.catalogueservice.dto.wrapper;

import com.example.catalogue.catalogueservice.dto.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class ItemDtoWrapper {

    @NotNull
    private ItemDto itemDto;

    @NotNull
    private List<Integer> categoryIds;

}
