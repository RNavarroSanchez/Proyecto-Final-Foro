package com.rnavarro.forofinal.models;

public class Foro {
    private String nameforo;
    private String id;

    public Foro(String nameforo) {
        this.nameforo = nameforo;
    }
    public Foro (){}

    public String getNameforo() {
        return nameforo;
    }

    public void setNameforo(String nameforo) {
        this.nameforo = nameforo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
