package com.revature.memestore.exceptions;

public class InternalServerException extends MemeStoreException{
    public InternalServerException(){
        super("An unexpected error has occurred!");
    }
}
