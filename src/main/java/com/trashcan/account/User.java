package com.trashcan.account;


import com.google.gson.Gson;
import com.trashcan.filemanager.FilePaths;

public class User {
    public int id = 0;
    public String name = "";
    public String mail = "";
    public int admid = 0;

    public User() {
        FilePaths fileManager = new FilePaths();
        Gson gson = new Gson();
    }

    public User(int id, String name, String mail) {
        this.id = id;
        this.name = name;
        this.mail = mail;
    }

    public User(int id, String name, String mail, int admid) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.admid = admid;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return this.mail;
    }

    public String getMail(int i) {
        String[] mail = this.mail.split("@");
        return mail[i];
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\nMail: " + this.getMail() + "\n";
    }
}
