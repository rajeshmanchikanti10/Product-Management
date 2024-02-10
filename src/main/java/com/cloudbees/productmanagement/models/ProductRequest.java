package com.cloudbees.productmanagement.models;

import com.cloudbees.productmanagement.enums.PriceModificationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class ProductRequest {
    private String productId;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Float price;
    @NotNull
    private int quantity;
    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PriceModificationType modificationType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float percentage;
}
