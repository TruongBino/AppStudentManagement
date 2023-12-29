package com.example.appstudentmanagement;

public class TeacherAccount {
    private String username;
    private String password;

    public TeacherAccount() {
        // Required default constructor
    }

    public TeacherAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
