package com.example.organisation.exception;

public class ResourceAlreadyExists extends RuntimeException {
    public ResourceAlreadyExists(String message){
        super(message);
    }
}
