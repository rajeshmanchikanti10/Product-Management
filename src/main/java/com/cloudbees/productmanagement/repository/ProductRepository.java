package com.cloudbees.productmanagement.repository;

import com.cloudbees.productmanagement.DBEntities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    Optional<Product> findByProductId(@Param("productId") String productId);

    void deleteByProductId(@Param("productId") String productId);

}
