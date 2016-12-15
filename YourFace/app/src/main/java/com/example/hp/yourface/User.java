package com.example.hp.yourface;

public class User { // сlass user
    String Name;
    String Password;

    void SetName(String _name) {
        this.Name = _name;
    }

    void SetPassword(String _password) {
        this.Password = _password;
    }

    String GetName() {
        return this.Name;
    }

    String GetPassword() {
        return this.Password;
    }
}
