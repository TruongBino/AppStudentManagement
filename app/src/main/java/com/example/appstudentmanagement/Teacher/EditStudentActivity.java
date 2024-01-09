package com.example.appstudentmanagement.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appstudentmanagement.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditStudentActivity extends AppCompatActivity {
    private EditText edtMaHS, edtName, edtBirth, edtClass, edtSDT, edtAddress, edtDetail;
    private ImageView imgAvatar;
    private ImageButton backButton;

    private Button btnUpdLoadStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        imgAvatar = findViewById(R.id.upload_student_image);
        edtMaHS = findViewById(R.id.edt_MaHS);
        edtName = findViewById(R.id.edt_Name);
        edtClass = findViewById(R.id.edt_Class);
        edtBirth = findViewById(R.id.edt_Birth);
        edtSDT = findViewById(R.id.edt_Phone);
        edtAddress = findViewById(R.id.edt_Address);
        edtDetail = findViewById(R.id.edt_Detail);
        btnUpdLoadStudent = findViewById(R.id.btn_UpdateStudent);
        btnUpdLoadStudent.setOnClickListener(v -> {
            // Hiển thị dialog xác nhận
            showConfirmationDialog();
        });
        backButton = findViewById(R.id.btn_back);
        // Xử lý sự kiện khi nút back được click
        backButton.setOnClickListener(v -> onBackPressed());

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
            int studentImageResId = intent.getIntExtra("photoUrl", R.drawable.man);

            // Hiển thị thông tin của học sinh
            imgAvatar.setImageResource(studentImageResId);
            edtMaHS.setText(studentMaHS);
            edtName.setText(studentName);
            edtClass.setText(studentClass);
            edtBirth.setText(studentBirth);
            edtAddress.setText(studentAddress);
            edtSDT.setText(studentSDT);
            edtDetail.setText(studentDetail);
        }
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Bạn có muốn lưu các thay đổi không?")
                .setPositiveButton("Lưu", (dialog, which) -> {
                    // Lưu thông tin học sinh và đóng màn hình chỉnh sửa
                    saveStudentInfo();
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    // Hủy bỏ việc chỉnh sửa và đóng màn hình chỉnh sửa
                    finish();
                })
                .show();
    }

    private void saveStudentInfo() {
        // Lấy thông tin học sinh đã được chỉnh sửa
        String studentId = edtMaHS.getText().toString();
        String updatedName = edtName.getText().toString();
        String updatedClass = edtClass.getText().toString();
        String updatedBirth = edtBirth.getText().toString();
        String updatedAddress = edtAddress.getText().toString();
        String updatedSDT = edtSDT.getText().toString();
        String updatedDetail = edtDetail.getText().toString();

        // Cập nhật thông tin học sinh trong cơ sở dữ liệu
        updateStudentInfo(studentId, updatedName, updatedClass, updatedBirth,updatedAddress, updatedSDT, updatedDetail);
    }

    private void updateStudentInfo(String studentId, String updatedName, String updatedClass, String updatedBirth,String updatedAddress, String updatedSDT, String updatedDetail) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Student").child(studentId);

        databaseReference.child("code").setValue(studentId);
        databaseReference.child("name").setValue(updatedName);
        databaseReference.child("studentClass").setValue(updatedClass);
        databaseReference.child("dateOfBirth").setValue(updatedBirth);
        databaseReference.child("address").setValue(updatedAddress);
        databaseReference.child("phone").setValue(updatedSDT);
        databaseReference.child("detail").setValue(updatedDetail);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Nếu cập nhật thành công, hiển thị thông báo
                Toast.makeText(EditStudentActivity.this, "Thông tin học sinh đã được cập nhật", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Nếu cập nhật không thành công, hiển thị thông báo lỗi
                Toast.makeText(EditStudentActivity.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
