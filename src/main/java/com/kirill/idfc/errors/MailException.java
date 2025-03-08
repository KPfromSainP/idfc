package com.kirill.idfc.errors;

public class MailException extends BruhException {
    public MailException(String message) {
        super("Error while sending mail: " + message);
    }
}
