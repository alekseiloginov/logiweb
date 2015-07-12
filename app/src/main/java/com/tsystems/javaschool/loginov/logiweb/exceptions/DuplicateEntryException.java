package com.tsystems.javaschool.loginov.logiweb.exceptions;

/**
 * Custom exception class to handle situation when an entered entry is unique and already placed into the database.
 */
public class DuplicateEntryException extends Exception {

    private String errorCode = "This entry has unique field and it's already present in the database";

    public DuplicateEntryException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
