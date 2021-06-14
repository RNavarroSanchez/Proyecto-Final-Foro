package com.rnavarro.forofinal.Notification;

import java.util.List;

public class Notification{

    private final List<String> tokenid;
    private final NotiMensaje notiMensaje;


    public Notification(List<String> tokenid, NotiMensaje notiMensaje) {
        this.tokenid = tokenid;
        this.notiMensaje = notiMensaje;
    }
}
