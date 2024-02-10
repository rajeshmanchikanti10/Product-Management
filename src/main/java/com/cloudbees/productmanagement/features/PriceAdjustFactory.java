package com.cloudbees.productmanagement.features;

import com.cloudbees.productmanagement.enums.PriceModificationType;

import java.util.HashMap;
import java.util.Map;

public class PriceAdjustFactory {
    private static final Map<PriceModificationType, PriceAdjuster> operations = new HashMap<>();

    static {
        operations.put(PriceModificationType.DISCOUNT, new Discount());
        operations.put(PriceModificationType.TAX, new Tax());
        operations.put(PriceModificationType.NONE, (amount, percentage) -> amount);
    }

    public static PriceAdjuster getOperation(PriceModificationType priceModificationType) {
        return operations.get(priceModificationType);
    }

}
