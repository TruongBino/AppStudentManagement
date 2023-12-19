package com.example.appstudentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDetailsActivity extends AppCompatActivity {
    private TextView tvMaHS, tvName, tvBirth, tvClass, tvSDT, tvAddress, tvDetail;
    private ImageView imgAvatar;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);


        imgAvatar = findViewById(R.id.img_student_image);
        tvMaHS = findViewById(R.id.tv_MaHS);
        tvName = findViewById(R.id.tv_Name);
        tvClass = findViewById(R.id.tv_Class);
        tvBirth = findViewById(R.id.tv_Birth);
        tvSDT = findViewById(R.id.tv_Phone);
        tvAddress = findViewById(R.id.tv_Address);
        tvDetail = findViewById(R.id.tv_Detail);


        backButton=findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> finish());

        // Lấy thông tin của học sinh từ intent
        Intent intent = getIntent();
        if (intent != null) {
                String studentMaHS = intent.getStringExtra("studentMaHS");
                String studentName = intent.getStringExtra("studentName");
                String studentClass = intent.getStringExtra("studentClass");
                String studentBirth = intent.getStringExtra("studentBirth");
                String studentSDT = intent.getStringExtra("studentSDT");
                String studentAddress = intent.getStringExtra("studentAddress");
                String studentDetail = intent.getStringExtra("studentDetail");
                int studentImageResId = intent.getIntExtra("studentImageResId", R.drawable.man);

                // Hiển thị thông tin của học sinh
                imgAvatar.setImageResource(studentImageResId);
                tvMaHS.setText("MaHS: " + studentMaHS);
                tvName.setText("Name: " + studentName);
                tvClass.setText("Class: " + studentClass);
                tvBirth.setText("Birth: " + studentBirth);
                tvSDT.setText("Phone: " + studentSDT);
                tvAddress.setText("Address: " + studentAddress);
                tvDetail.setText("Detail: " + studentDetail);
        }
    }
}
