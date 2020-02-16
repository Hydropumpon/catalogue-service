package com.example.catalogue.catalogueservice.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class OrderMessage {

    private Integer id;

    private String state;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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

    public static OrderMessageBuilder builder() {
        return new OrderMessageBuilder();
    }

    public static class OrderMessageBuilder {
        private Integer id;
        private String state;
        private BigDecimal amount;
        private List<OrderLine> orderLineList;
        private LocalDate approveDate;

        OrderMessageBuilder() {
        }

        public OrderMessageBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public OrderMessageBuilder state(String state) {
            this.state = state;
            return this;
        }

        public OrderMessageBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public OrderMessageBuilder orderLineList(List<OrderLine> orderLineList) {
            this.orderLineList = orderLineList;
            return this;
        }

        public OrderMessageBuilder approveDate(LocalDate approveDate) {
            this.approveDate = approveDate;
            return this;
        }

        public OrderMessage build() {
            return new OrderMessage(id, amount, orderLineList, approveDate, state);
        }

        public String toString() {
            return "OrderMessage.OrderMessageBuilder(id=" + this.id + ", state=" + this.state + ", amount=" +
                    this.amount +
                    ", orderLineList=" + this.orderLineList + ", approveDate=" + this.approveDate + ")";
        }
    }
}
