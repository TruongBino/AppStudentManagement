package com.example.appstudentmanagement.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appstudentmanagement.EditProfileStudentActivity;
import com.example.appstudentmanagement.ListExamActivity;
import com.example.appstudentmanagement.ListRankingActivity;

import com.example.appstudentmanagement.Student.LoginStudentActivity;

import com.example.appstudentmanagement.R;
import com.google.firebase.auth.FirebaseAuth;

public class StudentHomeActivity extends AppCompatActivity {
    private ImageButton btnLogOut,btnViewListStudent,btnProfile,btnRanking,btnExam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        FirebaseAuth.getInstance().signOut();
        initUI();
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(StudentHomeActivity.this, ListRankingActivity.class);
                startActivity(intent);
                // Chuyển về màn hình đăng nhập
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(StudentHomeActivity.this, LoginStudentActivity.class);
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
                Intent intent = new Intent(StudentHomeActivity.this, EditProfileStudentActivity.class);
                startActivity(intent);
            }
        });
        btnExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHomeActivity.this, ListExamActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initUI() {
        btnLogOut = findViewById(R.id.btn_LogOut);
        btnViewListStudent =findViewById(R.id.btn_ViewListStudent);
        btnProfile=findViewById(R.id.btn_EditProfile);
        btnRanking=findViewById(R.id.btn_Ranking);
        btnExam=findViewById(R.id.btn_ViewListExam);
    }
}
