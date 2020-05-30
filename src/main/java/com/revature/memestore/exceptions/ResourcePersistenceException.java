package com.revature.memestore.exceptions;

public class ResourcePersistenceException extends MemeStoreException{

    public ResourcePersistenceException(){
        super("The resource was not persisted!");
    }

    public ResourcePersistenceException(String message){
        super(message);
    }
}
