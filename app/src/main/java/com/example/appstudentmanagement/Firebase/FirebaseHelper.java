package com.example.appstudentmanagement.Firebase;

import com.example.appstudentmanagement.Variable.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {
    private DatabaseReference databaseReference;

    public FirebaseHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void addStudent(Student student, DatabaseReference.CompletionListener completionListener) {
        databaseReference.child("students").push().setValue(student, completionListener);
    }

}
