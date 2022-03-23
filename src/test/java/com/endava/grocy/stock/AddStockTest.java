package com.endava.grocy.stock;

import com.endava.grocy.TestBaseClass;
import com.endava.grocy.model.EntityType;
import com.endava.grocy.model.Stock;
import com.endava.grocy.model.TransactionType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class AddStockTest extends TestBaseClass {

    @Test
    public void shouldAddStock() {
        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION).createEntity(EntityType.QUANTITY_UNIT).createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Stock stock = dataProvider.getStock();
        //WHEN
        Response response = stockClient.addStock(productId, stock);
        //THEN
        response.then().statusCode(HttpStatus.SC_OK).body("size()", is(1)).body("id[0]", is(notNullValue())).body("amount[0]", is(stock.getAmount().toString()));
    }

    //BUG JIRA
    @Test
    public void shouldFailAddStockGivenTransactionTypeNotPurchase() {
        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION).createEntity(EntityType.QUANTITY_UNIT).createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Stock stock = dataProvider.getStock();
        stock.setTransactionType(TransactionType.CONSUME);

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_OK).body("size()", is(1)).body("id[0]", is(notNullValue())).body("amount[0]", is(stock.getAmount().toString()));
    }

    //BUG JIRA
    @Test
    public void shouldFailAddStockGivenNegativeAmount() {
        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION).createEntity(EntityType.QUANTITY_UNIT).createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Stock stock = dataProvider.getStock();
        stock.setAmount(-120.5);

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_OK).body("size()", is(1)).body("id[0]", is(notNullValue())).body("amount[0]", is(stock.getAmount().toString()));
    }

    //BUG JIRA
    @Test
    public void shouldFailAddStockGivenNegativePrice() {
        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION).createEntity(EntityType.QUANTITY_UNIT).createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Stock stock = dataProvider.getStock();
        stock.setPrice(-120.5);

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_OK).body("size()", is(1)).body("id[0]", is(notNullValue())).body("amount[0]", is(stock.getAmount().toString()));
    }

    @Test
    public void shouldFailAddStockGivenNonExistingProduct() {
        //GIVEN
        Stock stock = dataProvider.getStock();

        //WHEN
        Response response = stockClient.addStock(Integer.MAX_VALUE, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST).body("error_message", is("Product does not exist or is inactive"));
    }

    @Test
    public void shouldFailedAddStockDeletedProduct() {
        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION).createEntity(EntityType.QUANTITY_UNIT).createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        entityClient.deleteEntityById(EntityType.PRODUCTS, productId);
        Stock stock = dataProvider.getStock();

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST).body("error_message", is("Product does not exist or is inactive"));

    }

    @Test
    public void shouldAddStockOtherLocation() {
        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION).createEntity(EntityType.QUANTITY_UNIT).createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Integer locationId = grocyFixture.getLocation().getId();
        Stock stock = dataProvider.getStock();
        stock.setLocationId(locationId + 1);
        //WHEN
        Response response = stockClient.addStock(productId, stock);
        //THEN
        response.then().statusCode(HttpStatus.SC_OK).body("size()", is(1)).body("id[0]", is(notNullValue())).body("amount[0]", is(stock.getAmount().toString()));

    }

    @DisplayName("Add two stocks for the same product")
    @Test
    public void shouldAddTwoStocksSameProduct() {

        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION).createEntity(EntityType.QUANTITY_UNIT).createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Stock stockOne = dataProvider.getStock();
        Stock stockTwo = dataProvider.getStock();

        //WHEN
        stockClient.addStock(productId, stockOne);
        stockClient.addStock(productId, stockTwo);
        String currentStockAmount = String.format("%.2f", stockOne.getAmount() + stockTwo.getAmount());
        Response responseStockCurrent = stockClient.getStockById(productId);

        //THEN
        responseStockCurrent.then().statusCode(HttpStatus.SC_OK).body("stock_amount", is(currentStockAmount));
    }
}
