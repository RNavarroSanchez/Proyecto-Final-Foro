package com.rnavarro.forofinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rnavarro.forofinal.R;
import com.rnavarro.forofinal.models.Foro;
import com.rnavarro.forofinal.models.Message;

import java.util.ArrayList;

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.MessageHolder> {

    private ArrayList<Message> listamessage;
    private final Context context;
    private FirebaseFirestore db;
    private CollectionReference userCollectionmessage;
    private String correo;

    public AdapterMessage(Context context) {
        listamessage = new ArrayList<>();
        this.context = context;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = mAuth.getCurrentUser();
        correo = fUser.getEmail();
        db = FirebaseFirestore.getInstance();
        userCollectionmessage= db.collection("Foros");

    }

    @Override
    public AdapterMessage.MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mensaje, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMessage.MessageHolder holder, int position) {


    }

    @Override
    public int getItemCount() { return listamessage.size(); }


    static class MessageHolder extends RecyclerView.ViewHolder {




        public MessageHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}