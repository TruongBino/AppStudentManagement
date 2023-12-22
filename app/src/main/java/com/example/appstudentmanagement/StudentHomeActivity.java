package com.example.appstudentmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class StudentHomeActivity extends AppCompatActivity {
    private ImageButton btnLogOut,btnViewListStudent,btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        FirebaseAuth.getInstance().signOut();

        initUI();
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(StudentHomeActivity.this,LoginActivity.class);
                startActivity(intent);
                // Chuyển về màn hình đăng nhập
            }
        });
        btnViewListStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHomeActivity.this, ListViewStudentActivity.class);
                startActivity(intent);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initUI() {
        btnLogOut = findViewById(R.id.btn_LogOut);
        btnViewListStudent =findViewById(R.id.btn_ViewListStudent);
        btnProfile=findViewById(R.id.btn_EditProfile);
    }
}
