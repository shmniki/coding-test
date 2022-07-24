package com.adp.coding.test.exception;

public class ChangeNotSufficientException extends RuntimeException {
    public ChangeNotSufficientException(Double bill){
        super("Bill:" + bill + " is more than existing change." );
    }
}
