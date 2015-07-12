package com.tsystems.javaschool.loginov.logiweb.exceptions;

/**
 * Custom exception class to handle situation when an entered truck plate number is not valid.
 */
public class PlateNumberIncorrectException extends Exception {

    private String errorCode = "Plate number should contain 2 letters and 5 digits";

    public PlateNumberIncorrectException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
