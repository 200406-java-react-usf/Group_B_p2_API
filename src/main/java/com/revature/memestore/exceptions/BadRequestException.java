package com.revature.memestore.exceptions;

public class BadRequestException extends MemeStoreException {

    public BadRequestException(){
        super(400,"Invalid parameters provided");
    }

    public BadRequestException(String message){

        super(400,message);
    }



    public BadRequestException(String message, Throwable cause) {
        super(400,message, cause);
    }
}
