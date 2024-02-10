package com.cloudbees.productmanagement.service;

import com.cloudbees.productmanagement.DBEntities.Product;
import com.cloudbees.productmanagement.enums.ErrorCode;
import com.cloudbees.productmanagement.exceptions.ProductException;
import com.cloudbees.productmanagement.features.PriceAdjustFactory;
import com.cloudbees.productmanagement.features.PriceAdjuster;
import com.cloudbees.productmanagement.models.ProductRequest;
import com.cloudbees.productmanagement.models.ProductTransformer;
import com.cloudbees.productmanagement.repository.ProductRepository;
import jakarta.ws.rs.PATCH;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    private ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Optional<Product> createProduct(ProductRequest productRequest){
        if(productRequest.getPercentage()==null)
                productRequest.setPercentage(0f);
        PriceAdjuster priceAdjuster = PriceAdjustFactory.getOperation(productRequest.getModificationType());
        productRequest.setPrice(priceAdjuster.apply(productRequest.getPrice(),productRequest.getPercentage()));
        Product product = ProductTransformer.toCreateReq(productRequest);
        log.info(String.format("<---inserting {}---> %s",product.getName()));
        productRepository.save(product);
        return Optional.of(product);
    }
    //not allowed to apply discount and tax here
    public Optional<Product> updateProduct(ProductRequest productRequest) {
        if(productRequest.getPercentage()==null)
            productRequest.setPercentage(0f);
        PriceAdjuster priceAdjuster = PriceAdjustFactory.getOperation(productRequest.getModificationType());
        productRequest.setPrice(priceAdjuster.apply(productRequest.getPrice(),productRequest.getPercentage()));
        Optional<Product> dbProducts = productRepository.findByProductId(productRequest.getProductId());
        dbProducts.ifPresentOrElse(dbProduct -> {
            dbProduct.setName(productRequest.getName());
            dbProduct.setDescription(productRequest.getDescription());
            dbProduct.setPrice(productRequest.getPrice());
            dbProduct.setQuantity(productRequest.getQuantity());
        }, () -> {
            throw new ProductException(ErrorCode.UPDATION_ERROR, "Product doesn't exist in DB");
        });
        Product savable = dbProducts.orElse(null);
        log.info(String.format("updating product :%s with name:%s",savable.getProductId(),savable.getName()));
        productRepository.save(savable);
        return Optional.of(savable);


    }

    public Optional<ProductRequest> getProduct(String productId){
        return productRepository.findByProductId(productId).map(ProductTransformer::toDAO);
    }
    public List<ProductRequest> getAllProduct(){
        return productRepository.findAll().stream().map(ProductTransformer::toDAO).collect(Collectors.toList());
    }
    @Transactional
    public String deleteProduct(String productId) {
        Optional<Product> dbProducts = productRepository.findByProductId(productId);
        productRepository.deleteByProductId(productId);
        dbProducts.ifPresentOrElse(dbProduct -> {
            productRepository.deleteByProductId(productId);
        }, () -> {
            throw new ProductException(ErrorCode.NOT_FOUND, "Product doesn't exist in DB");
        });
        return "Deleted Product Successfully";
    }
    public void applyDiscountOrTax(String productId){

    }

}
