package com.example.catalogue.catalogueservice.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class OrderLine {

    private Integer itemId;

    private Integer quantity;

    public OrderLine(@JsonProperty Integer itemId, @JsonProperty Integer quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
