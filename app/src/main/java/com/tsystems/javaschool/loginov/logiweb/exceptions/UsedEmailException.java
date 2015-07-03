package com.tsystems.javaschool.loginov.logiweb.exceptions;

/**
 * Custom exception class to handle situation when a manager tries to sign-up with the already used email.
 */
public class UsedEmailException extends Exception {

    private String errorCode = "This email is already used";

    public UsedEmailException(String message, String errorCode) {
        super(message);
        this.errorCode=errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
