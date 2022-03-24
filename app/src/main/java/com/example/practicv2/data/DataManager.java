package com.example.practicv2.data;

public class DataManager {
    private static String token;

    public static String getToken(){
        return token;
    }

    public static void setToken(String token){
        DataManager.token = token;
    }
}
