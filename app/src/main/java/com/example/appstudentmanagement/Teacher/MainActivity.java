package com.example.appstudentmanagement.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.appstudentmanagement.ListRankingActivity;
import com.example.appstudentmanagement.Student.LoginStudentActivity;
import com.example.appstudentmanagement.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnLogOut,btnViewListStudent,btnProfile,btnViewListExam,btnRanking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListRankingActivity.class);
                startActivity(intent);
            }
        });
        btnViewListExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListExamActivity.class);
                startActivity(intent);
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginStudentActivity.class);
                startActivity(intent);
                // Chuyển về màn hình đăng nhập
            }
        });
        btnViewListStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListStudentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initUI() {
        btnLogOut = findViewById(R.id.btn_LogOut);
        btnViewListStudent =findViewById(R.id.btn_ViewListStudent);
        btnProfile=findViewById(R.id.btn_EditProfile);
        btnViewListExam=findViewById(R.id.btn_ViewListExam);
        btnRanking=findViewById(R.id.btn_Ranking);
    }
}