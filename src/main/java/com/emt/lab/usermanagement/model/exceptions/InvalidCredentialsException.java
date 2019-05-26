package com.emt.lab.usermanagement.model.exceptions;

public class InvalidCredentialsException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid Credentials.";
    }
}
