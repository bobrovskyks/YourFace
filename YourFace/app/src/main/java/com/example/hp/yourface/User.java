package com.example.hp.yourface;

public class User {

    private String UserName;
    private String Password;

    void SetUserName(String SetName) {
        this.UserName = SetName;
    }

    String GetUserName() {
        return this.UserName;
    }

    void SetUserPassword(String SetPassword) {
        this.Password = SetPassword;
    }

    String GetUserPassword() {
        return this.Password;
    }
}