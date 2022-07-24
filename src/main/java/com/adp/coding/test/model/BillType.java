package com.adp.coding.test.model;

import lombok.Getter;

public enum BillType {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    HUNDRED(100);

    @Getter
    private int bill;

    private BillType(int bill){
        this.bill = bill;
    }
}
