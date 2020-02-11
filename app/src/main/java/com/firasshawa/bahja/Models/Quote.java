package com.firasshawa.bahja.Models;

public class Quote {

    private String text;
    private int id;
    private boolean state;

    public Quote() {
        this.text = "Null";
        this.id = 0;
        this.state = false;
    }

    public Quote(String text, int id, boolean state) {
        this.text = text;
        this.id = id;
        this.state = state;
    }

    public Quote(String text, int id) {
        this.text = text;
        this.id = id;
        this.state = false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "text='" + text + '\'' +
                ", id=" + id +
                ", state=" + state +
                '}';
    }
}
