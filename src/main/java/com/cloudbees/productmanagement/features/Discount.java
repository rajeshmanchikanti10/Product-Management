package com.cloudbees.productmanagement.features;

public class Discount implements PriceAdjuster{
    @Override
    public float apply(float amount, float percentage){
        return amount * (1-(percentage/100));
    }
}
