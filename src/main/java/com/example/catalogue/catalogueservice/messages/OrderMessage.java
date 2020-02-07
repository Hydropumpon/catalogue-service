package com.example.catalogue.catalogueservice.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderMessage {

    private Integer id;

    private String state;

    private BigDecimal amount;

    private List<OrderLine> orderLineList;

    private LocalDate approveDate;

    @JsonCreator
    public OrderMessage(@JsonProperty("id") Integer id,
                        @JsonProperty("amount") BigDecimal amount,
                        @JsonProperty("orderLineList") List<OrderLine> orderLineList,
                        @JsonProperty("approveDate") LocalDate approveDate,
                        @JsonProperty("state") String state) {
        this.approveDate = approveDate;
        this.id = id;
        this.amount = amount;
        this.orderLineList = orderLineList;
        this.state = state;
    }
}
