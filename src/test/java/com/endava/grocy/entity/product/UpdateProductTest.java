package com.endava.grocy.entity.product;

import com.endava.grocy.TestBaseClass;
import com.endava.grocy.model.EntityType;
import com.endava.grocy.model.Product;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

public class UpdateProductTest extends TestBaseClass {

    @Test
    public void shouldUpdateProduct() {

        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);
        Integer id = grocyFixture.getProduct().getId();
        System.out.println(id);
        Integer locationID = grocyFixture.getLocation().getId();
        Integer quantityID = grocyFixture.getQuantityUnit().getId();
        Product product = dataProvider.getProduct(locationID, quantityID, quantityID);
        product.setName(product.getName() + " Modified");
//
//        //WHEN
        Response response1 = entityClient.updateEntityByID(EntityType.PRODUCTS, id, product);
//
//        //THEN
        response1.then().statusCode(HttpStatus.SC_OK);


    }
}
