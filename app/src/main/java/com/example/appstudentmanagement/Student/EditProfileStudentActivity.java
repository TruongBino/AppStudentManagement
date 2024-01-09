package com.example.appstudentmanagement.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appstudentmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileStudentActivity extends AppCompatActivity {

    private EditText edtEmail;
    private Button btnUpdateEmail, btnUpdatePassword;

    private FirebaseAuth auth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initUi();
        // Lấy email hiện tại của người dùng và hiển thị trong EditText
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String currentEmail = currentUser.getEmail();
            if (currentEmail != null) {
                Log.d("Email", currentEmail); // Ghi log email để kiểm tra
                edtEmail.setText(currentEmail);
            } else {
                Log.e("Email", "Current email is null");
            }
        } else {
            Log.e("Email", "Current user is null");
        }

        btnUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail();
            }
        });

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileStudentActivity.this, UpdatePasswordStudentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initUi() {
        edtEmail = findViewById(R.id.edt_Email); // Đảm bảo ID của EditText email là chính xác
        btnUpdateEmail = findViewById(R.id.btn_UpdateEmail);
        btnUpdatePassword = findViewById(R.id.btn_UpdatePassword);
    }

    private void changeEmail() {
        String newEmail = edtEmail.getText().toString().trim();

        if (newEmail.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ email mới", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String currentEmail = currentUser.getEmail();
            currentUser.updateEmail(newEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Thay đổi email thành công trong Firebase Authentication
                                updateEmailInRealtimeDatabase(currentUser.getUid(), newEmail);
                            } else {
                                // Xảy ra lỗi khi thay đổi email trong Firebase Authentication
                                Toast.makeText(EditProfileStudentActivity.this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void updateEmailInRealtimeDatabase(String userId, String email) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        userRef.child("email").setValue(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Thay đổi email thành công trong Firebase Realtime Database
                            Toast.makeText(EditProfileStudentActivity.this, "Cập nhật email thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            // Xảy ra lỗi khi thay đổi email trong Firebase Realtime Database
                            Toast.makeText(EditProfileStudentActivity.this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}