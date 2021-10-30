package com.example.acckeep.objects;

import java.io.Serializable;

public class Account implements Serializable {
    private int image;
    private String password;
    private String username;

    public Account(int image, String username, String password) {
        this.image = image;
        this.password = password;
        this.username = username;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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
