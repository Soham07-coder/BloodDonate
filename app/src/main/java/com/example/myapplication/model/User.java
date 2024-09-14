package com.example.myapplication.model;

public class User {
    String Name, Bloodgroup, Id, Email, IdNumber, Phonenumber, Profilepictureurl, search, type,key;

    public User() {
    }

    public User(String Name, String Bloodgroup, String Id, String Email, String IdNumber, String Phonenumber, String Profilepictureurl, String search, String type) {
        this.Name = Name;
        this.Bloodgroup = Bloodgroup;
        this.Id = Id;
        this.Email = Email;
        this.IdNumber = IdNumber;
        this.Phonenumber = Phonenumber;
        this.Profilepictureurl = Profilepictureurl;
        this.search = search;
        this.type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getBloodgroup() {
        return Bloodgroup;
    }

    public void setBloodgroup(String Bloodgroup) {
        this.Bloodgroup = Bloodgroup;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = Id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = Email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIdnumber() {
        return IdNumber;
    }

    public void setIdnumber(String idnumber) {
        this.IdNumber = idnumber;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.Phonenumber = Phonenumber;
    }

    public String getProfilepictureurl() {
        return Profilepictureurl;
    }

    public void setProfilepictureurl(String Profilepictureurl) {
        this.Profilepictureurl = Profilepictureurl;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
