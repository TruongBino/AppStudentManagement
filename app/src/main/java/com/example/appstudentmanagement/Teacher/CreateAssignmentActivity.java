package com.example.appstudentmanagement.Teacher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appstudentmanagement.R;
import com.example.appstudentmanagement.Variable.Assignment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CreateAssignmentActivity extends AppCompatActivity {
    private EditText subjectEditText;
    private EditText contentEditText;
    private ImageButton attachFileButton;
    private Button createAssignmentButton;
    private ImageButton btnBack;

    private DatabaseReference databaseRef;
    private StorageReference storageRef;

    private Uri fileUri; // Đường dẫn đến file được chọn từ thiết bị

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_assignment_layout);

        subjectEditText = findViewById(R.id.subjectEditText);
        contentEditText = findViewById(R.id.contentEditText);
        attachFileButton = findViewById(R.id.attachFileButton);
        createAssignmentButton = findViewById(R.id.createAssignmentButton);
        // Nút BackHome
        ImageButton backButton = findViewById(R.id.btn_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        databaseRef = FirebaseDatabase.getInstance().getReference("assignments");
        storageRef = FirebaseStorage.getInstance().getReference("assignment_files");

        createAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = subjectEditText.getText().toString();
                String content = contentEditText.getText().toString();
                // Kiểm tra xem người dùng đã chọn file phương tiện chưa
                if (fileUri != null) {
                    // Tạo một thư mục mới trên Firebase Storage để lưu trữ file phương tiện
                    StorageReference fileRef = storageRef.child(fileUri.getLastPathSegment());
                    fileRef.putFile(fileUri)
                            .addOnSuccessListener(taskSnapshot -> {
                                // Lấy đường dẫn đến file phương tiện sau khi tải lên thành công
                                // Sử dụng đường dẫn URL của file đã lưu trữ trên Firestore
                                String mediaUrl = fileUri.toString();
// Lưu trữ thông tin bài tập lên Firebase Realtime Database
                                Assignment assignment = new Assignment(subject, content, mediaUrl);
                                String key = databaseRef.push().getKey();
                                databaseRef.child(key).setValue(assignment);
                                Toast.makeText(CreateAssignmentActivity.this, "Bài tập đã được tạo và lưu trữ thành công", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                // Xử lý khi có lỗi xảy ra trong quá trình tải lên
                                Toast.makeText(CreateAssignmentActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    // Xử lý khi người dùng chưa chọn file phương tiện
                    Toast.makeText(CreateAssignmentActivity.this, "Vui lòng chọn file phương tiện trước khi tạo bài tập", Toast.LENGTH_SHORT).show();
                }
            }
        });

        attachFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở hộp thoại chọn file từ thiết bị
                Intent intent = new Intent();
                intent.setType("*/*"); // Chỉ cho phép chọn file PDF, bạn có thể thay đổi kiểu file tùy theo yêu cầu
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Chọn file phương tiện"), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            // Hiển thị tên file đã chọn
            Toast.makeText(this, "File đã chọn: " + fileUri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
        }
    }
}
