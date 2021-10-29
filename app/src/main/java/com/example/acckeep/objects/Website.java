package com.example.acckeep.objects;

import java.io.Serializable;

public class Website extends Credentials implements Serializable {
    private String website;

    public Website(String website, String username, String password) {
        super(username, password);
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String toString() {
        return "\nWebsite " + this.website + "\nUsername: " + getUsername() +
                "\nPassword: " + getPassword() + "\n";
    }
}
