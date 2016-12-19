package com.example.hp.yourface;

public class User { // сlass user
    String name;
    String password;

    void setName(String _name) {
        this.name = _name;
    }

    void setPassword(String _password) {
        this.password = _password;
    }

    String getName() {
        return this.name;
    }

    String getPassword() {
        return this.password;
    }
}
