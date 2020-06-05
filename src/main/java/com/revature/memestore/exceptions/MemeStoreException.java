package com.revature.memestore.exceptions;

public class MemeStoreException extends RuntimeException{

    private int status;

    public MemeStoreException(int status){this.status = status;}

    public MemeStoreException(int status, Throwable cause){

        super(cause);
        this.status = status;
    }

    public MemeStoreException(int status, String message){
        super(message);
        this.status = status;
    }

    public MemeStoreException(int status, String message, Throwable cause){

        super(message,cause);
        this.status = status;
    }

    public int getStatus(){
        return status;
    }



}
