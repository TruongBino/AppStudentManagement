package com.example.appstudentmanagement.Student;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appstudentmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordStudentActivity  extends AppCompatActivity {
    private Button btnUpdatePassword;
    private EditText edtPassword;
    private EditText edtPasswordNew1;
    private EditText edtPasswordNew2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_password);
        btnUpdatePassword=findViewById(R.id.btn_UpdatePasswordNew);
        edtPassword = findViewById(R.id.edt_Password);
        edtPasswordNew1 = findViewById(R.id.edt_PasswordNew1);
        edtPasswordNew2 = findViewById(R.id.edt_PasswordNew2);

        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               updatePassword();
            }
        });


    }
    private void updatePassword() {
        String currentPassword = edtPassword.getText().toString().trim();
        String newPassword1 = edtPasswordNew1.getText().toString().trim();
        String newPassword2 = edtPasswordNew2.getText().toString().trim();

        if (currentPassword.isEmpty() || newPassword1.isEmpty() || newPassword2.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword1.equals(newPassword2)) {
            Toast.makeText(this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            String oldPassword = currentPassword;

            // Đăng nhập lại để xác thực mật khẩu cũ
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, oldPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Xác thực mật khẩu cũ thành công
                                // Thực hiện cập nhật mật khẩu mới
                                currentUser.updatePassword(newPassword1)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(UpdatePasswordStudentActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(UpdatePasswordStudentActivity.this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(UpdatePasswordStudentActivity.this, "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
