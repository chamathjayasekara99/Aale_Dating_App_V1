package com.example.aale;

public class User {

    public String fullName,email,gender;
    public String age;
    public String height;
    public String weight;



    public User(){

    }

    public User(String fullName, String age, String email,String height,String weight,String gender){
        this.fullName=fullName;
        this.gender=gender;
        this.email=email;
        this.age=age;
        this.height=height;
        this.weight=weight;
    }

    public User(String fullName,String age, String height, String weight, String gender){
        this.fullName=fullName;
        this.gender=gender;
        this.age=age;
        this.height=height;
        this.weight=weight;

    }

    public String getFullName() {
        return fullName;
    }

}
