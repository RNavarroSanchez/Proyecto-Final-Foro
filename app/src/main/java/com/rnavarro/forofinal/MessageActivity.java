package com.rnavarro.forofinal;


import android.content.SharedPreferences;
import android.os.Bundle;

import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rnavarro.forofinal.Notification.NotiMensaje;
import com.rnavarro.forofinal.Notification.Notification;
import com.rnavarro.forofinal.adapters.AdapterForo;
import com.rnavarro.forofinal.adapters.AdapterMessage;
import com.rnavarro.forofinal.models.Foro;
import com.rnavarro.forofinal.models.Message;
import com.rnavarro.forofinal.webservice.MyClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageActivity extends AppCompatActivity {
    private static final String DB_USER_TOKEN=" ";
    private static final String id_token="idtoken";
    private List<Message> messages;
    private List<Notification> listatoken;
    private RecyclerView recyclerView;
    private AdapterMessage adapter;
    private FirebaseFirestore db;
    private CollectionReference userCollectionMessages;
    private TextInputEditText etmensaje;
    private Timestamp currentDateandTime;
    private String correo;
    private CollectionReference userCollectionToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_message);

        recyclerView = findViewById(R.id.recyclerMessage);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new AdapterMessage(this);
        recyclerView.setAdapter(adapter);
        etmensaje=findViewById(R.id.etMessage);
        FloatingActionButton flsend=findViewById(R.id.fl_send);


        currentDateandTime= new Timestamp(new Date());

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = mAuth.getCurrentUser();
        correo = fUser.getEmail();
        String nombreforo=getIntent().getExtras().getString("foro");


        db = FirebaseFirestore.getInstance();
        userCollectionToken=db.collection("Foros").document(nombreforo).collection("token");
        userCollectionMessages = db.collection("Foros").document(nombreforo).collection("messages");
        userCollectionMessages.orderBy("date").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                messages= new ArrayList<>();
                for (DocumentSnapshot document : value) {
                    Message message= document.toObject(Message.class);
                  //  message.setDate(document.getDate());
                    messages.add(message);
                }
                adapter.updateListMessage(messages);
                etmensaje.setText("");
            }
        });

        flsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToken(); guardarMessage();
            }
        });
    }
    private void addToken()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
       String token = preferences.getString(DB_USER_TOKEN," ");
        if(!token.isEmpty())
        {
            HashMap<String,String> hashMap= new HashMap<>();
            hashMap.put(id_token,token);

            userCollectionToken.document(token).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                }
            });
        }
    }
    private void sendNotificationMessage(String title,String msg)
    {

        userCollectionToken.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> tokens= new ArrayList<>();
                for(DocumentSnapshot document:queryDocumentSnapshots)
                {
                    Map<String,Object> map =document.getData();
                    tokens.add((String)map.get("token"));
                }
                sendNotiReal(title,msg,tokens);
            }
        });
    }
    private  void sendNotiReal(String title,String msg,List<String>token)
    {
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String fecha= simpleDateFormat.format(new Date());
        NotiMensaje notimensaje= new NotiMensaje(msg,title,fecha);
        Notification notif= new Notification(token,notimensaje);
        Retrofit retrofit= new Retrofit.Builder().baseUrl(MyClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
          MyClient myClient= retrofit.create(MyClient.class);
        Call<Object> objectCall=myClient.sendNotification(notif);
        objectCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.isSuccessful())
                {
                    Object object= response.body();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }


    private void guardarMessage()
    {
      String messagesend=etmensaje.getText().toString();

        if(!messagesend.isEmpty())
        {
            Message message = new Message(currentDateandTime,correo,messagesend,"makea");
            userCollectionMessages.add(message).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(MessageActivity.this, "Añadido! ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                   sendNotificationMessage(correo,messagesend);
                }
            });
        }
        else {
            Toast.makeText(this, "El campo nombre es obligatorio", Toast.LENGTH_SHORT).show();
        }
    }

}
