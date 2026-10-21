package com.example.fixsnap;


public class User {
    private  String username;
    private  String uid;

    private  String email;
    private  String phonenumber;

    public User(){

    }

    public User(String username, String uid, String email, String phonenumber) {
        this.username = username;
        this.uid = uid;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }





    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
