package com.rnavarro.forofinal;


import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.rnavarro.forofinal.adapters.AdapterForo;
import com.rnavarro.forofinal.adapters.AdapterMessage;
import com.rnavarro.forofinal.models.Foro;
import com.rnavarro.forofinal.models.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MessageActivity extends AppCompatActivity {
    private List<Message> messages;
    private RecyclerView recyclerView;
    private AdapterMessage adapter;
    private FirebaseFirestore db;
    private CollectionReference userCollectionMessages;
    private TextInputEditText etmensaje;
    private Timestamp currentDateandTime;
    private String correo;


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
                guardarMessage();
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

                }
            });
        }
        else {
            Toast.makeText(this, "El campo nombre es obligatorio", Toast.LENGTH_SHORT).show();
        }
    }

}
