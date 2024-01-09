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

import com.example.appstudentmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpStudentActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    private TextView tv_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tv_Login = findViewById(R.id.tvLogin);
        tv_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpStudentActivity.this, LoginStudentActivity.class);
                startActivity(intent);
            }
        });
        initUi();
        initListener();
    }

    private void initListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });
    }

    private void onClickSignUp() {
        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.createUserWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Lưu thông tin người dùng vào Realtime Database
                            saveUserDataToRealtimeDatabase(strEmail);
                            Intent intent = new Intent(SignUpStudentActivity.this, LoginStudentActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpStudentActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserDataToRealtimeDatabase(String email) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String validPath = email.replace(".", "_"); // Thay thế "." bằng "_"
        DatabaseReference userRef = database.getReference("users").child(validPath);
        // Lưu các thông tin khác của tài khoản người dùng vào Realtime Database
        userRef.child("email").setValue(email);
        userRef.child("role").setValue("student"); // Ví dụ: lưu trường "role" để chỉ định vai trò của người dùng
        // Thêm các xử lý thành công hoặc thất bại nếu cần
    }
    private void initUi() {
        edtEmail = findViewById(R.id.edt_Email);
        edtPassword = findViewById(R.id.edt_Password);
        btnSignUp = findViewById(R.id.btn_Signup);
        progressDialog = new ProgressDialog(this);
    }
}