package com.eslamwael74.inq.expmeal;

/**
 * Created by Eslam Wael on 2/17/2018.
 */

public class User {

    String name;

    String email;

    String phone;

    String password;

    public User(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
