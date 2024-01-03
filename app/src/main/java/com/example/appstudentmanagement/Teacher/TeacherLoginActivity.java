package com.example.appstudentmanagement.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appstudentmanagement.Admin.AdminLoginActivity;
import com.example.appstudentmanagement.Student.LoginStudentActivity;
import com.example.appstudentmanagement.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherLoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnAdminLg, btnStudentLg;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("teachers");

        etUsername = findViewById(R.id.edt_UserNameLg);
        etPassword = findViewById(R.id.edt_PasswordLg);
        btnLogin = findViewById(R.id.btn_Login);
        btnAdminLg = findViewById(R.id.btn_AdminLg);
        btnStudentLg = findViewById(R.id.btn_StudentLg);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();

                mDatabase.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                String savedPassword = userSnapshot.child("password").getValue().toString();
                                if (savedPassword.equals(password)) {
                                    // Đăng nhập thành công
                                    Toast.makeText(TeacherLoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(TeacherLoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Đăng nhập thất bại
                                    Toast.makeText(TeacherLoginActivity.this, "Đăng nhập Không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            // Đăng nhập thất bại
                            Toast.makeText(TeacherLoginActivity.this, "Đăng nhập Không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(TeacherLoginActivity.this, "Lỗi kết nối đến cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnAdminLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherLoginActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });

        btnStudentLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherLoginActivity.this, LoginStudentActivity.class);
                startActivity(intent);
            }
        });
    }
}
