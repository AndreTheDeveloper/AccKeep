package com.example.acckeep;

import java.io.Serializable;

public class Credentials implements Serializable {
    private String website;
    private String password;
    private String username;

    public Credentials(String website, String username, String password) {
        this.website = website;
        this.password = password;
        this.username = username;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String toString() {
        return "\nWebsite/Application: " + this.website + "\nUsername: " + this.username +
                "\nPassword: " + this.password + "\n";
    }
}
