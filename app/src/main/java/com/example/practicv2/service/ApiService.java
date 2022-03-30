package com.example.practicv2.service;

import com.example.practicv2.network.model.MoviesResponse;
import com.example.practicv2.network.model.SignInBody;
import com.example.practicv2.network.model.SignInResponse;
import com.example.practicv2.network.model.SignUpBody;
import com.example.practicv2.network.model.UserResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/auth/login")
    Call<SignInResponse> doSignInRequest(@Body SignInBody signInBody);

    @POST("/auth/register")
    Call<ResponseBody> doSignUpRequest(@Body SignUpBody signUpBody);

    @GET("/movies")
    Call<List<MoviesResponse>> getMovies(@Query("filter") String filter);

    @GET("/user")
    Call<List<UserResponse>> getUserInfo(@Header("Authorization") String Token);
}
