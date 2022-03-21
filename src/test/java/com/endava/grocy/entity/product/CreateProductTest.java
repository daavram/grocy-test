package com.endava.grocy.entity.product;

import com.endava.grocy.TestBaseClass;
import com.endava.grocy.model.EntityType;
import com.endava.grocy.model.Product;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

public class CreateProductTest extends TestBaseClass {

    @Test
    public void shouldCreateProduct() {

        //Given
        grocyFixture.createEntity(EntityType.LOCATION).createEntity(EntityType.QUANTITY_UNIT);
        Integer locationID = grocyFixture.getLocation().getId();
        Integer quantityID = grocyFixture.getQuantityUnit().getId();
        Product product = dataProvider.getProduct(locationID, quantityID, quantityID);

        //When
        Response response = entityClient.createEntity(EntityType.PRODUCTS, product);

        //Then
        response.then().statusCode(HttpStatus.SC_OK).body("created_object_id", notNullValue());
    }
}
