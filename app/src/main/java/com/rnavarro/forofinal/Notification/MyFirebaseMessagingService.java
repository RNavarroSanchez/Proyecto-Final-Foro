package com.rnavarro.forofinal.Notification;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;


import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rnavarro.forofinal.MainActivity;
import com.rnavarro.forofinal.R;


import java.util.Map;

public class MyFirebaseMessagingService  extends FirebaseMessagingService {

    private static final String TAG="MyFirebaseService";
    private static final String CHANNEL_ID="5";
    private static final int NOTIFICATION_ID=111;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getData().size()>0)
        {
          Log.d(TAG, "Datos del mensaje: " + remoteMessage.getData());
            Map<String,String> datos= remoteMessage.getData();
            String titulo = datos.get("titulo");
            String body = datos.get("mensaje");
            String fecha = datos.get("fecha");
            sendNotification(titulo,body,fecha);
        }
    }

    private void sendNotification(String titulo, String body, String fecha) {
        String tituloNoti ="Mensaje de "+titulo;
        Intent i= new Intent(this, MainActivity.class);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
       PendingIntent pendingIntent=PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

       String channel_id = CHANNEL_ID;

        NotificationCompat.Builder builderNotification=
                new NotificationCompat.Builder(this,channel_id)
                .setSmallIcon(R.drawable.icon)
                        .setContentTitle(tituloNoti)
                .setContentText(fecha+body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, builderNotification.build());


    }
}
