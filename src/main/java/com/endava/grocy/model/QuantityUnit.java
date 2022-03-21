package com.endava.grocy.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuantityUnit extends Entity {

    @JsonProperty("name_plural")
    private String namePlural;
}
