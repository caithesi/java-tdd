package com.tddinaction;

public class MissingValueException extends RuntimeException {
    public MissingValueException(String message) {
        super(message);
    }
}
