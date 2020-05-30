package com.revature.memestore.exceptions;

public class ResourceNotFoundException extends MemeStoreException{
    public ResourceNotFoundException(){
        super("No resource found using provided criteria");
    }
    public ResourceNotFoundException(String message){
        super(message);
    }
}
