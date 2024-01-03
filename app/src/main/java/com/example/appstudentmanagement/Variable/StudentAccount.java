package com.example.appstudentmanagement.Variable;

public class StudentAccount {
    private String email;
    private String password;

    public StudentAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
