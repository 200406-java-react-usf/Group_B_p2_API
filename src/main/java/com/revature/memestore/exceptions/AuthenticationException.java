package com.revature.memestore.exceptions;

public class AuthenticationException extends MemeStoreException {

    public AuthenticationException(){
        super(401,"Authentication Failed!");
    }

    public AuthenticationException(String message){
        super(401,message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(401,message, cause);
    }
}
