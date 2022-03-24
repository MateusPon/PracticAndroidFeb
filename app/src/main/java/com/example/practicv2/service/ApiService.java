package com.example.practicv2.service;

import com.example.practicv2.network.model.SignInBody;
import com.example.practicv2.network.model.SignInResponse;
import com.example.practicv2.network.model.SignUpBody;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/auth/login")
    Call<SignInResponse> doSignInRequest(@Body SignInBody signInBody);

    @POST("/auth/register")
    Call<ResponseBody> doSignUpRequest(@Body SignUpBody signUpBody);
}
