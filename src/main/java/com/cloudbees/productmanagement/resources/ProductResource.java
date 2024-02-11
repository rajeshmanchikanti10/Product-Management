package com.cloudbees.productmanagement.resources;

import com.cloudbees.productmanagement.enums.ErrorCode;
import com.cloudbees.productmanagement.exceptions.ProductException;
import com.cloudbees.productmanagement.models.ProductRequest;
import com.cloudbees.productmanagement.models.ProductTransformer;
import com.cloudbees.productmanagement.response.ProductResponse;
import com.cloudbees.productmanagement.service.ProductService;
import com.cloudbees.productmanagement.utils.ResponseUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
public class ProductResource {
    private ProductService productService;
    @Autowired
    public ProductResource(ProductService productService){
        this.productService = productService;
    }
    @PostMapping
    public ProductResponse<ProductRequest> createProduct( @RequestBody @Valid ProductRequest productRequest){
        return productService.createProduct(productRequest)
                .map(ProductTransformer::toDAO)
                .map(ResponseUtils::toProductResponse).orElseThrow(()->new ProductException(ErrorCode.CREATION_ERROR,"Creation Error"));
    }
    @GetMapping("/{id}")
    public ProductResponse<ProductRequest> getProduct(@PathVariable("id") String productId ){
        return productService.getProduct(productId).map(ResponseUtils::toProductResponse).orElseThrow(()->new ProductException(ErrorCode.NOT_FOUND,"Product Not Found"));

    }
    @GetMapping
    public ProductResponse<List<ProductRequest>> getAllProducts(){
        return ResponseUtils.toProductResponse(productService.getAllProduct());
    }
    @PutMapping
    public ProductResponse<ProductRequest> updateProduct( @RequestBody  @Valid  ProductRequest productRequest){
        return productService.updateProduct(productRequest).map(ProductTransformer::toDAO).map(ResponseUtils::toProductResponse).orElseThrow(()->new ProductException(ErrorCode.UPDATION_ERROR,"Updation Exception"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id")String productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.deleteProduct(productId));
    }
}
