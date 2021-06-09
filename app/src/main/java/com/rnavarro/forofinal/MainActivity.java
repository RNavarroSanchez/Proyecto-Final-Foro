package com.rnavarro.forofinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.rnavarro.forofinal.models.Foro;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Foro> foros;
    private RecyclerView recyclerView;
    private AdapterForo adapter;
    private FirebaseFirestore db;
    private CollectionReference userCollectionforos;
    String nombreforo;
    private EditText etNombreforo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foro_list);

        recyclerView = findViewById(R.id.recyclerforo);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new AdapterForo(this);
        recyclerView.setAdapter(adapter);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = mAuth.getCurrentUser();
        String correo = fUser.getEmail();

        db = FirebaseFirestore.getInstance();
        userCollectionforos = db.collection("Foros");
            userCollectionforos.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    foros= new ArrayList<>();
                    for (DocumentSnapshot document : value) {
                        Foro foro= document.toObject(Foro.class);
                        foro.setId(document.getId());
                        foros.add(foro);
                }
                    adapter.updateList(foros);
                }
            });


       FloatingActionButton btaddforo= findViewById(R.id.AddForo);

        btaddforo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_foro,null);
                builder.setView(view);

                etNombreforo= view.findViewById(R.id.et_nameforo);
                
                    builder.setPositiveButton("añadir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                              guardarforo();
                        }
                    });

                        builder.create().show();
            }
        });

    }
    private void guardarforo()
    {
        nombreforo= etNombreforo.getText().toString();

        if(!nombreforo.isEmpty())
        {
            Foro foro = new Foro(nombreforo);
            userCollectionforos.add(foro).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(MainActivity.this, "Añadido! ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            Toast.makeText(this, "El campo nombre es obligatorio", Toast.LENGTH_SHORT).show();
        }
    }


}
