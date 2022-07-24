package com.adp.coding.test.controller;

import com.adp.coding.test.model.request.ChangeBillRequest;
import com.adp.coding.test.model.response.CoinChangeResponse;
import com.adp.coding.test.service.BillChangeService;
import com.adp.coding.test.validator.BillChangeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@Validated
public class BillChangeController {

    private BillChangeService billChangeService;

    private BillChangeValidator billChangeValidator;

    public BillChangeController(BillChangeService billChangeService, BillChangeValidator billChangeValidator) {
        this.billChangeService = billChangeService;
        this.billChangeValidator = billChangeValidator;
    }

    @InitBinder("changeBillRequest")
    public void initBinder(WebDataBinder  binder) {
        binder.addValidators(billChangeValidator);
    }


    @PostMapping("/api/bill/change")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CoinChangeResponse changeBill(@Valid @RequestBody ChangeBillRequest changeBillRequest){
        return billChangeService.changeBill(changeBillRequest);
    }

}
