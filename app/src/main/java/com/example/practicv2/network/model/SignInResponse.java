package com.example.practicv2.network.model;

import com.google.gson.annotations.SerializedName;

public class SignInResponse {
    @SerializedName("token")
    private String token;

    public String getToken(){
        return token;
    }
    public void setToken(String token){
        this.token = token;
    }
}
