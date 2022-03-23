package com.endava.grocy.stock;

import com.endava.grocy.TestBaseClass;
import com.endava.grocy.model.EntityType;
import com.endava.grocy.model.Stock;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class GetPriceHistoryTest extends TestBaseClass {


    @DisplayName("Get price history from one stock with one price of the product")
    @Test
    public void shouldGetProductPriceHistoryOneStock() {

        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION).createEntity(EntityType.QUANTITY_UNIT).createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Stock stock = dataProvider.getStock();

        //WHEN
        stockClient.addStock(productId, stock);
        Response responseStockCurrent = stockClient.getPriceHistoryById(productId);

        //THEN
        responseStockCurrent.then().statusCode(HttpStatus.SC_OK)
                .body("price[0]", is(notNullValue()))
                .body("price[1]", is(nullValue()));
    }

    @DisplayName("Get price history from two stocks for the same product")
    @Test
    public void shouldGetProductPriceHistoryTwoStocks() {

        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION).createEntity(EntityType.QUANTITY_UNIT).createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Stock stockOne = dataProvider.getStock();
        Stock stockTwo = dataProvider.getStock();

        //WHEN
        stockClient.addStock(productId, stockOne);
        stockClient.addStock(productId, stockTwo);
        Response responseStockCurrent = stockClient.getPriceHistoryById(productId);

        //THEN
        responseStockCurrent.then().statusCode(HttpStatus.SC_OK)
                .body("price[0]", is(notNullValue()))
                .body("price[1]", is(notNullValue()));
    }
}
