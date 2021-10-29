package com.example.acckeep.objects;

import java.io.Serializable;

public class Credentials implements Serializable {
    private String password;
    private String username;

    public Credentials(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
