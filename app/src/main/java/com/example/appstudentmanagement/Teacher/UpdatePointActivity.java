package com.example.appstudentmanagement.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appstudentmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdatePointActivity extends AppCompatActivity {
    private EditText edtConduct, edtGPA, edtAcademicAbility;
    private TextView tvCode;
    private ImageButton backButton;
    private Button btnUpdatePoint;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_update_point);

        mDatabase = FirebaseDatabase.getInstance().getReference("Student");

        btnUpdatePoint = findViewById(R.id.btn_UpdatePoint);
        edtConduct = findViewById(R.id.edt_Conduct);
        edtGPA = findViewById(R.id.edt_ScoreGPA);
        edtAcademicAbility = findViewById(R.id.edt_ScoreAcademicAbility);
        tvCode=findViewById(R.id.tv_code);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("studentMaHS")) {
            final String studentCode = intent.getStringExtra("studentMaHS");
            String studentConduct = intent.getStringExtra("studentConduct");
            String studentGPA = intent.getStringExtra("studentGPA");
            String studentAcademicAbility = intent.getStringExtra("studentAcademicAbility");
            tvCode.setText(studentCode);
            edtConduct.setText(studentConduct);
            edtGPA.setText(studentGPA);
            edtAcademicAbility.setText(studentAcademicAbility);
        }
        btnUpdatePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentCode=tvCode.getText().toString().trim();
                String conduct = edtConduct.getText().toString().trim();
                String gpa = edtGPA.getText().toString().trim();
                String academicAbility = edtAcademicAbility.getText().toString().trim();

                updatePoint(studentCode, conduct, gpa, academicAbility);
            }
        });

        backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updatePoint(String studentCode, String conduct, String gpa, String academicAbility) {
        mDatabase.child(studentCode).child("scoreHanhKiem").setValue(conduct);
        mDatabase.child(studentCode).child("scoreDTB").setValue(gpa);
        mDatabase.child(studentCode).child("scoreHocLuc").setValue(academicAbility)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdatePointActivity.this, "Điểm đã được cập nhật thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            String errorMessage = task.getException().getMessage(); // Lấy thông báo lỗi từ Task
                            Toast.makeText(UpdatePointActivity.this, "Đã xảy ra lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
