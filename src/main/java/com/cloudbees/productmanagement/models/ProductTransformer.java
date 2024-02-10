package com.cloudbees.productmanagement.models;

import com.cloudbees.productmanagement.DBEntities.Product;
import com.cloudbees.productmanagement.utils.IdGenerator;
import org.hibernate.id.IdentifierGenerator;

public class ProductTransformer {
    public static Product toCreateReq(ProductRequest request) {
        request.setProductId(IdGenerator.generate());
        return toDAO(request);
    }

    public static Product toDAO(ProductRequest request) {
        return Product.builder()
                .productId(request.getProductId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }

    public static ProductRequest toDAO(Product product) {
        return ProductRequest.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
}
