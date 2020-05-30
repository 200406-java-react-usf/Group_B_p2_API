package com.revature.memestore.exceptions;

public class BadRequestException extends MemeStoreException {

    public BadRequestException(){
        super("Invalid parameters provided");
    }

    public BadRequestException(String message){
        super(message);
    }
}
