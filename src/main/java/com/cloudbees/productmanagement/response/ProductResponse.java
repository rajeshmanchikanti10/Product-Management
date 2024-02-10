package com.cloudbees.productmanagement.response;

import com.cloudbees.productmanagement.models.ProductRequest;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class ProductResponse<T> {
    boolean success;
    T data;
}
