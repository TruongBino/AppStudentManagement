package com.example.appstudentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import com.example.appstudentmanagement.TeacherAccount;


public class ListAccountTeacherActivity extends AppCompatActivity {
    private Button btnAddAccTeacher;
    private ImageButton backButton;
    private ListView teacherAccountListView;
    private TeacherAccountAdapter adapter;
    private DatabaseReference teachersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listacc_teacher);
        backButton=findViewById(R.id.btn_Back);
        backButton.setOnClickListener(v -> finish());


        teacherAccountListView = findViewById(R.id.teacherAccountListView);

        teachersRef = FirebaseDatabase.getInstance().getReference("teachers");
        teachersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TeacherAccount> TeacherAccountAdapter = new ArrayList<>();
                for (DataSnapshot teacherSnapshot : dataSnapshot.getChildren()) {
                    String username = teacherSnapshot.child("username").getValue(String.class);
                    String password = teacherSnapshot.child("password").getValue(String.class);
                    TeacherAccount teacherAccount = new TeacherAccount(username, password);
                    TeacherAccountAdapter.add(teacherAccount);
                }
                adapter = new TeacherAccountAdapter(ListAccountTeacherActivity.this, TeacherAccountAdapter);
                teacherAccountListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

        btnAddAccTeacher=findViewById(R.id.addTeacherAccountButton);
        btnAddAccTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ListAccountTeacherActivity.this,RegisterAccountTeacherActivity.class);
                startActivity(intent);
            }
        });
    }


}