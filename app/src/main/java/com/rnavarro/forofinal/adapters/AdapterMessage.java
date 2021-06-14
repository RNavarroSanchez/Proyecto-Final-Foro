package com.rnavarro.forofinal.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.IccOpenLogicalChannelResponse;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rnavarro.forofinal.MessageActivity;
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
       String date= parseDateToddMMyyyy(message.getDate().toDate());
        holder.date.setText(date);
        holder.email.setText(message.getEmail());
        holder.message.setText(message.getMenssage());
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);

        String colorreceptor = preferences.getString("listcolor_receptor","verde");
        String colorremisor = preferences.getString("listcolor_emisor","amarillo");

        if (colorremisor.equals("morado"))
        {
            holder.laymessage.setBackgroundResource(R.color.purple_200);
        }
        else if (colorremisor.equals("verde"))
        {
            holder.laymessage.setBackgroundResource(R.color.teal_200);
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


        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            email= itemView.findViewById(R.id.tv_email);
            date=itemView.findViewById(R.id.tv_date);
            message=itemView.findViewById(R.id.tv_mensaje);
            laymessage=itemView.findViewById(R.id.lymesgge);

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