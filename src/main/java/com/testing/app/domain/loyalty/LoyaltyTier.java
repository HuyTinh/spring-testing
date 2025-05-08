package com.testing.app.domain.loyalty;

import com.testing.app.common.enums.AbstractEnum;

public enum LoyaltyTier implements AbstractEnum<Integer> {
    SILVER(0, "SILVER"),
    GOLD(1, "GOLD"),
    PLATINUM(2, "PLATINUM");

    private final Integer value;
    private final String name;

    LoyaltyTier(Integer value, String name) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }
}
