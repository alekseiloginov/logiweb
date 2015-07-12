package com.tsystems.javaschool.loginov.logiweb.exceptions;

/**
 * Custom exception class to handle situation when an entered driver's plate number is not found in the database.
 */
public class PlateNumberNotFoundException extends Exception {

    private String errorCode = "Entered plate number is not found in the database";

    public PlateNumberNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
