package com.revature.memestore.exceptions;

public class MemeStoreException extends RuntimeException{

    public MemeStoreException(String message){
        super(message);
    }

    public MemeStoreException(Throwable cause){
        super(cause);
    }

    public MemeStoreException(String message, Throwable cause){
        super(message,cause);
    }



}
