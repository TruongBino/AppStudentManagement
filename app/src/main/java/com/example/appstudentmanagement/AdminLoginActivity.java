package com.example.appstudentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton,btnStudentLg,btnTeacherLg;

    private DatabaseReference adminRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        initUI();
        btnStudentLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminLoginActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btnTeacherLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminLoginActivity.this,TeacherLoginActivity.class);
                startActivity(intent);
            }
        });
        // Khởi tạo Firebase Realtime Database
        adminRef = FirebaseDatabase.getInstance().getReference().child("admins");


        // Thêm tài khoản admin mặc định vào Firebase Realtime Database
        adminRef.child("admin").child("password").setValue("admin123");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                adminRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Kiểm tra mật khẩu
                            String savedPassword = snapshot.child("password").getValue(String.class);
                            if (savedPassword.equals(password)) {
                                // Đăng nhập thành công
                                Toast.makeText(AdminLoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                // Thực hiện các hành động sau khi đăng nhập thành công
                                Intent intent = new Intent(AdminLoginActivity.this, AdminHomeActivity.class);
                                startActivity(intent);
                            } else {
                                // Mật khẩu không đúng
                                Toast.makeText(AdminLoginActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Tên đăng nhập không tồn tại
                            Toast.makeText(AdminLoginActivity.this, "Tên đăng nhập không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Xử lý lỗi
                        Toast.makeText(AdminLoginActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initUI() {
        usernameEditText = findViewById(R.id.edt_UserName);
        passwordEditText = findViewById(R.id.edt_Password);
        loginButton = findViewById(R.id.btn_LogIn);
        btnTeacherLg=findViewById(R.id.btn_TeacherLg);
        btnStudentLg=findViewById(R.id.btn_StudentLg);
    }
}