package com.example.appstudentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDetailsActivity extends AppCompatActivity {
    private TextView tvMaHS, tvName, tvBirth, tvClass, tvSDT, tvAddress, tvDetail,tvConduct,tvAcademicAbility,tvGPA;
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
        tvConduct = findViewById(R.id.tv_Conduct);
        tvAcademicAbility = findViewById(R.id.tv_ScoreAcademicAbility);
        tvGPA = findViewById(R.id.tv_ScoreGPA);

        backButton=findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> finish());

        // Lấy thông tin của học sinh từ intent
        Intent intent = getIntent();
        if (intent != null) {
                String studentMaHS = intent.getStringExtra("code");
                String studentName = intent.getStringExtra("name");
                String studentClass = intent.getStringExtra("studentClass");
                String studentBirth = intent.getStringExtra("dateOfBirth");
                String studentSDT = intent.getStringExtra("phone");
                String studentAddress = intent.getStringExtra("address");
                String studentDetail = intent.getStringExtra("detail");
                String studentConDuct = getIntent().getStringExtra("scoreHanhKiem");
                String studentGPA = getIntent().getStringExtra("scoreDTB");
                String studentAcademicAbility = getIntent().getStringExtra("scoreHocLuc");
                int studentImageResId = intent.getIntExtra("photoUrl", R.drawable.man);

                // Hiển thị thông tin của học sinh
                imgAvatar.setImageResource(studentImageResId);
                tvMaHS.setText("Mã học sinh : " + studentMaHS);
                tvName.setText("Họ và tên : " + studentName);
                tvClass.setText("Lớp : " + studentClass);
                tvBirth.setText("Ngày sinh: " + studentBirth);
                tvSDT.setText("Số điện thoại: " + studentSDT);
                tvAddress.setText("Địa chỉ: " + studentAddress);
                tvDetail.setText("Thông tin chi tiết: " + studentDetail);
                tvConduct.setText(studentConDuct);
                tvGPA.setText(studentGPA);
                tvAcademicAbility.setText(studentAcademicAbility);
        }

    }

}
