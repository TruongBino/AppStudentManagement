package com.example.appstudentmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeActivity extends AppCompatActivity {
    private ImageButton btnLogOut,btnViewListStudent,btnProfile,btnAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        initUI();
        //checkUserRole();
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("loggedOut", true);
                editor.apply();

                // Chuyển về màn hình đăng nhập
                goToLoginScreen();
            }

            private void goToLoginScreen() {
                Intent intent = new Intent(AdminHomeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btnViewListStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ListStudentActivity.class);
                startActivity(intent);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminManagementActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initUI() {
        btnLogOut = findViewById(R.id.btn_LogOut);
        btnViewListStudent =findViewById(R.id.btn_ViewListStudent);
        btnProfile=findViewById(R.id.btn_EditProfile);
        btnAdmin=findViewById(R.id.btn_AdminPage);
    }
}
