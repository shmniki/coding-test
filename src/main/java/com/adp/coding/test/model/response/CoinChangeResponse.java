package com.adp.coding.test.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoinChangeResponse {
    private int oneCent;
    private int fiveCents;
    private int tenCents;
    private int twentyFiveCents;
}
