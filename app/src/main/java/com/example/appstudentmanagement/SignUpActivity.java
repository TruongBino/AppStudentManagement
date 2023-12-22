package com.example.appstudentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword, edtUserName;
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
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
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
                            saveUserInformation(strEmail, edtUserName.getText().toString());
                            // Lưu email tài khoản vào SharedPreferences
                            saveEmailToSharedPreferences(strEmail);
                            // Chuyển về màn hình đăng nhập và tự động điền email tài khoản
                            goToLoginScreen(strEmail);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void initUi() {
        edtUserName = findViewById(R.id.edt_DisPlayName);
        edtEmail = findViewById(R.id.edt_Email);
        edtPassword = findViewById(R.id.edt_Password);
        btnSignUp = findViewById(R.id.btn_Signup);
        progressDialog = new ProgressDialog(this);
    }
    private void saveEmailToSharedPreferences(String email) {
        SharedPreferences sharedPref = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", email);
        editor.apply();
    }

    private void goToLoginScreen(String email) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        intent.putExtra("email", email); // Truyền email tài khoản qua Intent
        startActivity(intent);
        finishAffinity();
    }

    private void saveUserInformation(String email, String displayName) {
        String role = "student"; // Mặc định tất cả các tài khoản là tài khoản sinh viên
        // Lưu thông tin DisplayName và vai trò vào SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("displayName", displayName);
        editor.putString("role", role);
        editor.apply();
        // Cập nhật vai trò và DisplayName người dùng trên Firebase
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Vai trò và DisplayName người dùng đã được cập nhật thành công trên Firebase
                                Log.d("UserInformation", "Cập nhật thông tin người dùng thành công!");
                            } else {
                                // Xử lý khi cập nhật thông tin người dùng không thành công
                                Log.d("UserInformation", "Cập nhật thông tin người dùng không thành công!");
                            }
                        }
                    });
        }
    }
}