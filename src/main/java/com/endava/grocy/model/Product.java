package com.endava.grocy.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Product extends Entity {

    @JsonProperty("location_id")
    private Integer locationId;
    @JsonProperty("min_stock_amount")
    private Integer minStockAmount;
    @JsonProperty("qu_id_stock")
    private Integer quantityStockId;
    @JsonProperty("qu_id_purchase")
    private Integer quantityPurchaseId;
    @JsonProperty("qu_factor_purchase_to_stock")
    private Number quantityFactorPurchaseToStock;

}
