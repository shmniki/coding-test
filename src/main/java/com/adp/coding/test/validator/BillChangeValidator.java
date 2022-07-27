package com.adp.coding.test.validator;

import com.adp.coding.test.model.BillType;
import com.adp.coding.test.model.request.ChangeBillRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;

@Component
public class BillChangeValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ChangeBillRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangeBillRequest  changeBillRequest = (ChangeBillRequest) target;
        boolean found = Arrays.stream(BillType.values())
                .anyMatch(billType -> billType.getBill() == changeBillRequest.getBill());

        if(!found){
            errors.reject("Bill is invalid");
        }
    }
}
