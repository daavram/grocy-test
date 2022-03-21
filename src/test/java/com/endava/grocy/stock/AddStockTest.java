package com.endava.grocy.stock;

import com.endava.grocy.TestBaseClass;
import com.endava.grocy.model.EntityType;
import com.endava.grocy.model.Stock;
import com.endava.grocy.model.TransactionType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class AddStockTest extends TestBaseClass {

    @Test
    public void shouldAddStock() {
        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Stock stock = dataProvider.getStock();
        //WHEN
        Response response = stockClient.addStock(productId, stock);
        //THEN
        response.then().statusCode(HttpStatus.SC_OK)
                .body("size()", is(1))
                .body("id[0]", is(notNullValue()))
                .body("amount[0]", is(stock.getAmount().toString()));
    }

    @Test
    public void shouldFailAddStockGivenTransactionTypeNotPurchase() {
        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Stock stock = dataProvider.getStock();
        stock.setTransactionType(TransactionType.CONSUME);

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_OK)
                .body("size()", is(1))
                .body("id[0]", is(notNullValue()))
                .body("amount[0]", is(stock.getAmount().toString()));
    }

    @Test
    public void shouldFailAddStockGivenNegativeAmount() {
        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Stock stock = dataProvider.getStock();
        stock.setAmount(-120.5);

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_OK)
                .body("size()", is(1))
                .body("id[0]", is(notNullValue()))
                .body("amount[0]", is(stock.getAmount().toString()));
    }

    @Test
    public void shouldFailAddStockGivenNegativePrice() {
        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Stock stock = dataProvider.getStock();
        stock.setPrice(-120.5);

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_OK)
                .body("size()", is(1))
                .body("id[0]", is(notNullValue()))
                .body("amount[0]", is(stock.getAmount().toString()));
    }

    @Test
    public void shouldFailAddStockGivenNonExistingProduct() {
        //GIVEN
        Stock stock = dataProvider.getStock();

        //WHEN
        Response response = stockClient.addStock(Integer.MAX_VALUE, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error_message", is("Product does not exist or is inactive"));
    }

}
