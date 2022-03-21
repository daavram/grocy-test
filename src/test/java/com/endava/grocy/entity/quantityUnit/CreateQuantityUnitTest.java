package com.endava.grocy.entity.quantityUnit;

import com.endava.grocy.TestBaseClass;
import com.endava.grocy.model.EntityType;
import com.endava.grocy.model.QuantityUnit;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

public class CreateQuantityUnitTest extends TestBaseClass {
    @Test
    public void shouldCreateQuantityUnit() {

        //Given
        QuantityUnit quantityUnit = dataProvider.getQuantityUnit();

        //does this quantity already exist
        //GET quantitylist -> check if it is exists
        //delete if it is exists

        //When
        Response response = entityClient.createEntity(EntityType.QUANTITY_UNIT, quantityUnit);

        //Then
        response.then().statusCode(HttpStatus.SC_OK).body("created_object_id", notNullValue());
    }
}
