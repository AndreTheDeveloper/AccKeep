package com.example.acckeep.objects;

import java.io.Serializable;

public class Application extends Credentials implements Serializable {
    private String app;

    public Application(String app, String username, String password) {
        super(username, password);
        this.app = app;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String toString() {
        return "\nApplication: " + this.app + "\nUsername: " + getUsername() +
                "\nPassword: " + getPassword() + "\n";
    }
}
