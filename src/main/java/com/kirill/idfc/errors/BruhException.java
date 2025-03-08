package com.kirill.idfc.errors;

public class BruhException extends RuntimeException {
    public BruhException(String message) {
        super("Bruh moment: " + message);
    }
}
