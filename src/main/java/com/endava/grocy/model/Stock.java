package com.endava.grocy.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stock {

    private Double amount;
    @JsonProperty("best_before_date")
    private String bestBeforeDate;
    @JsonProperty("transaction_type")
    private TransactionType transactionType;
    private Double price;
    @JsonProperty("location_id")
    private Integer locationId;
    @JsonProperty("shopping_location_id")
    private Integer shoppingLocationId;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return super.toString();
        }
    }
}
