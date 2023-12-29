package com.example.appstudentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AdminManagementActivity extends AppCompatActivity {
    private ImageButton btnLogOut,btnLvStudent,btnAdminHome,btnLvTeacher;
    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_management);
    btnLogOut = findViewById(R.id.btn_LogOut);
    btnLvStudent =findViewById(R.id.imgbtn_lvStudent);
    btnAdminHome=findViewById(R.id.imgbtn_lvHome);
    btnLvTeacher=findViewById(R.id.imgbtn_lvTeacher);
    btnLvTeacher.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(AdminManagementActivity.this, ListAccountTeacherActivity.class);
            startActivity(intent);
        }
    });

        btnAdminHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminManagementActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    btnLvStudent.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AdminManagementActivity.this, ListAccountStudentActivity.class);
            startActivity(intent);
            finish();
        }
    });
    btnLogOut.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(AdminManagementActivity.this, LoginStudentActivity.class);
            startActivity(intent);
            finish();
        }
    });
    }
}
