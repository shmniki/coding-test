package com.adp.coding.test.model;

import lombok.Getter;

public enum ChangeType {

    ONE(0.01),
    FIVE(0.05),
    TEN(0.10),
    TWENTY_FIVE(0.25);

    @Getter
    private double coin;

    private ChangeType(double coin) {
        this.coin = coin;
    }
}
