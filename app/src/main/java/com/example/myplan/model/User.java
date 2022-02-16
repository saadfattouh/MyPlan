package com.example.myplan.model;

public class User {

    private int id;
    private String email;
    private String fullName;
    private String phone;
    private int type;

    public User(int id, String email, String fullName, String phone, int type) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
