package com.example.appstudentmanagement.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appstudentmanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterAccountTeacherActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnRegister;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account_teacher);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etUsername = findViewById(R.id.et_teacher_username);
        etPassword = findViewById(R.id.et_teacher_password);
        btnRegister = findViewById(R.id.btn_register_teacher);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                createTeacherAccount(username, password);
            }
        });
    }
    private void createTeacherAccount(String username, String password) {
        // Tạo một ID duy nhất cho tài khoản giáo viên
        String teacherId = mDatabase.child("teachers").push().getKey();

        // Lưu thông tin tài khoản giáo viên vào Firebase Realtime Database
        mDatabase.child("teachers").child(teacherId).child("username").setValue(username);
        mDatabase.child("teachers").child(teacherId).child("password").setValue(password);

        // Chuyển đến trang đăng nhập
        Intent intent = new Intent(RegisterAccountTeacherActivity.this, TeacherLoginActivity.class);
        startActivity(intent);
        finish();
    }
}