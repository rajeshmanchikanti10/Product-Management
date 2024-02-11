package com.cloudbees.productmanagement.models;

import com.cloudbees.productmanagement.enums.PriceModificationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class ProductRequest {
    private String productId;
    @NotNull(message = "name should not be null")
    @NotBlank
    private String name;
    @NotNull(message = "description shouldn't be null")
    @NotBlank
    private String description;
    @Positive(message = "Value must be greater than zero")
    private float price;
    @Positive(message = "Value must be greater than zero")
    private int quantity;
    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PriceModificationType modificationType;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private float percentage;
}
