package com.tsystems.javaschool.loginov.logiweb.exceptions;

/**
 * Custom exception class to handle situation when a user (an email) is not found in the app's database.
 */
public class UserNotFoundException extends Exception {

    private String errorCode = "User not found in the database";

    public UserNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode=errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
