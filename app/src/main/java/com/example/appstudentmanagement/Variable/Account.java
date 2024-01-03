package com.example.appstudentmanagement.Variable;

public class Account{
    private String displayName;
    private String email;

    public Account(String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }
}