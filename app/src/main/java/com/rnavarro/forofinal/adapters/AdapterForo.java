package com.rnavarro.forofinal.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.rnavarro.forofinal.MessageActivity;
import com.rnavarro.forofinal.R;
import com.rnavarro.forofinal.models.Foro;
import com.rnavarro.forofinal.models.Message;

import java.util.ArrayList;
import java.util.List;

public class AdapterForo extends RecyclerView.Adapter<AdapterForo.ForoHolder> {
    private ArrayList<Foro> listaforo;
    private final Context context;
    private FirebaseFirestore db;
    private CollectionReference userCollectionforos;
    private String correo;

    public AdapterForo(Context context) {
        listaforo = new ArrayList<>();
        this.context = context;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = mAuth.getCurrentUser();
        correo = fUser.getEmail();
        db = FirebaseFirestore.getInstance();
        userCollectionforos= db.collection("Foros");

    }


    @Override
    public AdapterForo.ForoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_foro, parent, false);
        return new ForoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForoHolder holder, int position) {
        final Foro foro =listaforo.get(position);
        holder.nombreforo.setText(foro.getNameforo());
        holder.itemforo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context,MessageActivity.class);
             //    String foro= userCollectionforos.document().getId();
                i.putExtra("foro",foro.getId());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaforo.size();
    }

    public void updateList(List<Foro> lista) {
        listaforo.clear();
        listaforo.addAll(lista);
        notifyDataSetChanged();
    }


    static class ForoHolder extends RecyclerView.ViewHolder {

        private final TextView nombreforo;
        private final ConstraintLayout itemforo;


        public ForoHolder(@NonNull View itemView) {
            super(itemView);
            nombreforo= itemView.findViewById(R.id.tv_itemForo);
            itemforo=itemView.findViewById(R.id.layout_item_foro);

        }
    }
}
