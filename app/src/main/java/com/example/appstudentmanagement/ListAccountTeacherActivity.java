package com.example.appstudentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class ListAccountTeacherActivity extends AppCompatActivity {
    private Button btnAddAccTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listacc_teacher);

        btnAddAccTeacher=findViewById(R.id.addTeacherAccountButton);
        btnAddAccTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ListAccountTeacherActivity.this,RegisterAccountTeacherActivity.class);
                startActivity(intent);
            }
        });
    }

}