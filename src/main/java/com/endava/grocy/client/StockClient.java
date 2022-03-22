package com.endava.grocy.client;

import com.endava.grocy.model.Stock;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StockClient extends BaseClient {

    public Response addStock(Integer productId, Stock stock) {
        return getBasicRestConfig()
                .contentType(ContentType.JSON)
                .body(stock)
                .pathParam("productId", productId)
                .post("/stock/products/{productId}/add");
    }
    public Response getStockById(Integer productId) {
        return getBasicRestConfig()
                .contentType(ContentType.JSON)
                .pathParam("productId", productId)
                .get("/stock/products/{productId}");
    }
}
