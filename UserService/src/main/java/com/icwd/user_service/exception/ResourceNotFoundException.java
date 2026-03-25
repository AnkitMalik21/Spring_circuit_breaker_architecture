package com.icwd.user_service.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(){
        super("Resource not found on database");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
