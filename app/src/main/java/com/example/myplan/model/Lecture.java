package com.example.myplan.model;

public class Lecture {

    private int id;
    private String pdf;
    private String title;

    public Lecture(int id, String pdf, String title) {
        this.id = id;
        this.pdf = pdf;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
