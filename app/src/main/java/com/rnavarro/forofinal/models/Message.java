package com.rnavarro.forofinal.models;

import com.google.firebase.Timestamp;

public class Message {

    private Timestamp date;
    private String email;
    private String message;
    private String nick;

    public Message(Timestamp date, String email, String menssage, String nick) {
        this.date = date;
        this.email = email;
        this.message = menssage;
        this.nick = nick;
    }
    public Message(){}

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMenssage() {
        return message;
    }

    public void setMenssage(String menssage) {
        this.message = menssage;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}



