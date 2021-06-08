package com.tema6.foroestech.retrofit;

import com.tema6.foroestech.models.MyNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MyClient {

    String BASE_URL = "https://fcm.googleapis.com/";
    String SERVER_TOKEN = "AAAAvuahWA0:APA91bHlBtw9RZMAOOwLt1SGmheXdECl3ekVjy9gonYqQyQmf2nbgdkjY0eimzP0HlLEII5W402KoMGRL7dSepOTLJfkRtTLaUb3f_8M0yLVLBOsiLXAQUPZ3vJ4vLXstv7voxDXkuiK";

    @Headers({"Authorization:key=" + SERVER_TOKEN, "Content-Type:application/json"})
    @POST("fcm/send")
    Call<Object> sendNotification(@Body MyNotification mynotif);
}
