package com.cloudbees.productmanagement.DBEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity(name = "Products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column(unique = true,nullable = false)
    String productId;
    @Column
    String name;
    @Column
    String description;
    @Column
    int quantity;
    @Column
    Float price;
}
