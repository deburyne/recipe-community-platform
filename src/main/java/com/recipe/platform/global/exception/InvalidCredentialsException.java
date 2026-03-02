package com.recipe.platform.global.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message){
        super(message);
    }
}
