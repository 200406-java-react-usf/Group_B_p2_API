package com.revature.memestore.exceptions;

public class AuthenticationException extends MemeStoreException {

    public AuthenticationException(){
        super("Authentication Failed!");
    }

    public AuthenticationException(String message){
        super(message);
    }
}
