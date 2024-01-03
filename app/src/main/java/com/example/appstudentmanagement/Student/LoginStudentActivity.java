package com.example.appstudentmanagement.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appstudentmanagement.Admin.AdminLoginActivity;
import com.example.appstudentmanagement.R;
import com.example.appstudentmanagement.SignUpStudentActivity;
import com.example.appstudentmanagement.Student.StudentHomeActivity;
import com.example.appstudentmanagement.Teacher.TeacherLoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginStudentActivity extends AppCompatActivity {

    private TextView textViewSignUp;

    private EditText edtEmail, edtPassword;
    private Button btnLogin,btnTeacherLg,btnAdminLg;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textViewSignUp = findViewById(R.id.tvSignUp);
        edtEmail = findViewById(R.id.edt_EmailLg);
        edtPassword = findViewById(R.id.edt_PasswordLg);
        btnLogin = findViewById(R.id.btn_Login);
        btnAdminLg=findViewById(R.id.btn_AdminLg);
        btnTeacherLg=findViewById(R.id.btn_TeacherLg);
        btnAdminLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginStudentActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
        btnTeacherLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginStudentActivity.this, TeacherLoginActivity.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginStudentActivity.this, SignUpStudentActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("email")) {
            String email = intent.getStringExtra("email");
            // Tự động điền email tài khoản vào trường email
            edtEmail.setText(email);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogin();
            }
        });
    }

    private void onClickLogin() {
        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        progressDialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            FirebaseUser currentUser = auth.getCurrentUser();
                            if (currentUser != null) {
                                Intent intent = new Intent(LoginStudentActivity.this, StudentHomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(LoginStudentActivity.this, "Đăng nhập không thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}