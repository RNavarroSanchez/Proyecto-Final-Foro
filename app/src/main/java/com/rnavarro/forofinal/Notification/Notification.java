package com.rnavarro.forofinal.Notification;

import java.util.List;

public class Notification{

    private final List<String> registration_ids;
    private final NotiMensaje data;


    public Notification(List<String> tokenid, NotiMensaje notiMensaje) {
        this.registration_ids = tokenid;
        this.data = notiMensaje;
    }

    
}
