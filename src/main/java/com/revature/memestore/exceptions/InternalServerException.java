package com.revature.memestore.exceptions;

public class InternalServerException extends MemeStoreException{
    public InternalServerException(){
        super(500,"An unexpected error has occurred!");

    }

    public InternalServerException(String message) {
        super(500,message);
    }



    public InternalServerException(String message, Throwable cause) {
        super(500,message, cause);
    }
}
