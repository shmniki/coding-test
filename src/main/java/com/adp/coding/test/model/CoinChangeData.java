package com.adp.coding.test.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoinChangeData {
    private Double total;
    private int availableChange;
}
