package com.cloudbees.productmanagement.utils;

import com.cloudbees.productmanagement.response.ProductResponse;

public class ResponseUtils {
   public static <T> ProductResponse<T> toProductResponse(T data) {
       return ProductResponse.<T>builder().success(true).data(data).build();
   }
}
