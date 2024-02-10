package com.cloudbees.productmanagement.enums;

public enum PriceModificationType {
    DISCOUNT("DISCOUNT"),
    NONE("NONE"),
    TAX("TAX");
    private String type;
    PriceModificationType(String type){
        this.type=type;
    }
}
