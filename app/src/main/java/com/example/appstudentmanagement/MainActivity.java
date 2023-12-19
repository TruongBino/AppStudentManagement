package com.example.appstudentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnLogOut,btnAddStudent,btnViewListStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogOut = findViewById(R.id.btn_LogOut);
        btnAddStudent = findViewById(R.id.btn_AddStudent);
        btnViewListStudent =findViewById(R.id.btn_ViewListStudent);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                // Sau khi đăng xuất, chuyển người dùng đến màn hình đăng nhập hoặc màn hình chính khác
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Đóng Activity hiện tại

                //
            }
        });
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityAddStudent.class);
                startActivity(intent);
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
}