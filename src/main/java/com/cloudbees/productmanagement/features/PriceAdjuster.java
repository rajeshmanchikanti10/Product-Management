package com.cloudbees.productmanagement.features;

public interface PriceAdjuster {
    float apply(float amount,float percentage);
}
