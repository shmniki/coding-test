package com.adp.coding.test.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ChangeBillRequest {
    @NotNull(message = "should have value")
    @Min(1)
    private Integer bill;
}
