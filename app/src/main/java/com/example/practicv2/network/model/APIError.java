package com.example.practicv2.network.model;

public class APIError {
    private String error;

    public APIError(String error){
        this.error = error;
    }

    public String message(){
        return error;
    }
}
