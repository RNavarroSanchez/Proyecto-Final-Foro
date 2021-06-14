package com.rnavarro.forofinal.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rnavarro.forofinal.R;
import com.rnavarro.forofinal.models.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.MessageHolder> {

    private ArrayList<Message> listamessage;
    private final Context context;


    public AdapterMessage(Context context) {
        listamessage = new ArrayList<>();
        this.context = context;

    }

    @Override
    public AdapterMessage.MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mensaje, parent, false);
        return new MessageHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull AdapterMessage.MessageHolder holder, int position) {
        final  Message message=listamessage.get(position);

        String  emisor=message.getEmail();
        LinearLayout.LayoutParams parametros= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

       FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = mAuth.getCurrentUser();
        String correo = fUser.getEmail();

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        String colorreceptor = preferences.getString("listcolor_receptor","verde");
        String colorremisor = preferences.getString("listcolor_emisor","amarillo");

        if(emisor.equals(correo))
        {

            String date= parseDateToddMMyyyy(message.getDate().toDate());
            holder.date.setText(date);
            holder.email.setText(message.getEmail());
            holder.message.setText(message.getMenssage());
            holder.laymessageext.setGravity(Gravity.END);
            if (colorremisor.equals("rojo"))
            {
                holder.laymessage.setBackgroundResource(R.color.red);
            }
            else if (colorremisor.equals("verde"))
            {
                holder.laymessage.setBackgroundResource(R.color.teal_200);
            }
            else if (colorremisor.equals("azul"))
            {
                holder.laymessage.setBackgroundResource(R.color.blue);
            }
            else if (colorremisor.equals("amarillo"))
            {
                holder.laymessage.setBackgroundResource(R.color.yellow);
            }
            else if (colorremisor.equals("morado"))
            {
                holder.laymessage.setBackgroundResource(R.color.purple_200);
            }

        }
        else
        {

            String date= parseDateToddMMyyyy(message.getDate().toDate());
            holder.date.setText(date);
            holder.email.setText(message.getEmail());
            holder.message.setText(message.getMenssage());
            holder.laymessage.setLayoutParams(parametros);
            holder.laymessageext.setGravity(Gravity.START);

            if (colorreceptor.equals("rojo"))
            {
                holder.laymessage.setBackgroundResource(R.color.red);
            }
            else if (colorreceptor.equals("verde"))
            {
                holder.laymessage.setBackgroundResource(R.color.teal_200);
            }
            else if (colorreceptor.equals("azul"))
            {
                holder.laymessage.setBackgroundResource(R.color.blue);
            }
            else if (colorreceptor.equals("amarillo"))
            {
                holder.laymessage.setBackgroundResource(R.color.yellow);
            }
            else if (colorreceptor.equals("morado"))
            {
                holder.laymessage.setBackgroundResource(R.color.purple_200);
            }

        }


    }

    @Override
    public int getItemCount() { return listamessage.size(); }

    public void updateListMessage(List<Message> lista) {
        listamessage.clear();
        listamessage.addAll(lista);
        notifyDataSetChanged();
    }


    static class MessageHolder extends RecyclerView.ViewHolder {

        private  final TextView email;
        private final TextView date;
        private final TextView message;
        private final LinearLayout laymessage;
        private final LinearLayout laymessageext;



        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            email= itemView.findViewById(R.id.tv_email);
            date=itemView.findViewById(R.id.tv_date);
            message=itemView.findViewById(R.id.tv_mensaje);
            laymessage=itemView.findViewById(R.id.lymesggedentro);
            laymessageext=itemView.findViewById(R.id.lymesgge);


        }
    }

    public String parseDateToddMMyyyy(Date time) {
        String outputPattern = "EEE, MMM d '-' HH:mm";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        outputFormat.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        String str = outputFormat.format(time);
        return str;
    }
}