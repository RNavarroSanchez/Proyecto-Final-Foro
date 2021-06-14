package com.rnavarro.forofinal.webservice;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MyClient {

    String BASE_URL = "https://fcm.googleapis.com/";
    String SERVER_TOKEN = "AAAAWWMql3A:APA91bEAHddvsZyGE3g2zPQHInmotef5EIF-cKtrTHo_rbpgyr-jGjgtem3Vpn-scNmDBzcGlpw2U8IhvbkIh5zD1p_8c4g1-VqsYvbchzlbX0jrhAj87GuoVmEkemX-kbIU1d-Dor6n";

//    @Headers({"Authorization:key=" + SERVER_TOKEN, "Content-Type:application/json"})
//    @POST("fcm/send")
//    Call<Object> sendNotification(@Body MyNotification mynotif);
}
