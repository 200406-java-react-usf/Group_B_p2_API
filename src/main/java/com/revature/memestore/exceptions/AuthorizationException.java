package com.revature.memestore.exceptions;

public class AuthorizationException extends MemeStoreException{

    public AuthorizationException(){
        super("Authorization Failed!");
    }

    public AuthorizationException(String message) {
        super(message);
    }

}
