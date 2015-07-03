package com.tsystems.javaschool.loginov.logiweb.exceptions;

/**
 * Custom exception class to handle situation when a user's password is not the same as in the app's database.
 */
public class PasswordIncorrectException extends Exception {

    private String errorCode = "User not found in the database";

    public PasswordIncorrectException(String message, String errorCode) {
        super(message);
        this.errorCode=errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
