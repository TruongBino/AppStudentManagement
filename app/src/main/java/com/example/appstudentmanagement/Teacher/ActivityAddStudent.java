package com.example.appstudentmanagement.Teacher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appstudentmanagement.R;
import com.example.appstudentmanagement.Variable.Student;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ActivityAddStudent extends AppCompatActivity {
    private ProgressDialog progressDialog;
    ImageView upLoadStudentImg;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private EditText MaHS,edtName,edtBirth,edtClass,edtSDT,edtAddress,edtDetail;
    private EditText edtscoreHanhKiem,edtscoreDTB,edtscoreHocLuc;
    private ImageButton fileDetailScore;
    private Button btnAddStudent;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        upLoadStudentImg = findViewById(R.id.upload_student_image);
        MaHS = findViewById(R.id.et_MaHS);
        edtName = findViewById(R.id.et_name);
        edtClass = findViewById(R.id.et_class);
        edtBirth = findViewById(R.id.et_Birth);
        edtAddress = findViewById(R.id.et_address);
        edtSDT = findViewById(R.id.et_phone);
        edtDetail = findViewById(R.id.et_detail);

        fileDetailScore=findViewById(R.id.img_FileDetailScore);
        edtscoreHanhKiem = findViewById(R.id.et_HanhKiem);
        edtscoreDTB = findViewById(R.id.et_ScoreDTB);
        edtscoreHocLuc = findViewById(R.id.et_ScoreHocLuc);

        btnAddStudent=findViewById(R.id.btn_AddStudent);
        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        storage = FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        // Chọn ảnh từ thư viện
        upLoadStudentImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        // push data lên firebase
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPushData();
            }
        });
    }

    // Mở thư viện ảnh để chọn ảnh
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Xử lý khi người dùng chọn ảnh từ thư viện
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            upLoadStudentImg.setImageURI(imageUri);
        }
    }

    private void onClickPushData() {
        if (isValidData()) {
            if (imageUri != null) {
                // Tải ảnh lên Firebase Storage
                StorageReference fileReference = storageReference.child("students/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));
                fileReference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            // Lấy URL của ảnh sau khi tải lên thành công
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful()) ;
                            Uri downloadUrl = urlTask.getResult();
                            String photoUrl = downloadUrl.toString();

                            // Lấy thông tin sinh viên từ các EditText
                            String codeValue = MaHS.getText().toString();
                            String nameValue = edtName.getText().toString();
                            String classValue = edtClass.getText().toString();
                            String birthValue = edtBirth.getText().toString();
                            String addressValue = edtAddress.getText().toString();
                            String phoneValue = edtSDT.getText().toString();
                            String detailValue = edtDetail.getText().toString();
                            //Khai báo FIle điểm
                            String HanhKiemValue = edtscoreHanhKiem.getText().toString();
                            String DTBValue = edtscoreDTB.getText().toString();
                            String HocLucValue = edtscoreHocLuc.getText().toString();


                            // Tạo đối tượng Student
                            Student student = new Student(photoUrl, codeValue, nameValue, classValue, birthValue, addressValue, phoneValue, detailValue, HanhKiemValue, DTBValue, HocLucValue);
                            if (isStudentCodeExists(student.getCode())) {
                                Toast.makeText(this, "Mã học sinh đã tồn tại", Toast.LENGTH_SHORT).show();
                            } else {
                                // Đẩy dữ liệu lên Firebase Realtime Database
                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Student");
                                myRef.push().setValue(student)
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                // Nếu thành công, hiển thị thông báo
                                                Toast.makeText(ActivityAddStudent.this, "Dữ liệu đã được lưu thành công", Toast.LENGTH_SHORT).show();
                                            } else {
                                                // Nếu không thành công, hiển thị thông báo lỗi
                                                Toast.makeText(ActivityAddStudent.this, "Lưu dữ liệu không thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(e -> {
                            // Xử lý khi không thể tải ảnh lên
                            Toast.makeText(ActivityAddStudent.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });

            } else {
                // Xử lý khi người dùng chưa chọn ảnh
                Toast.makeText(this, "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Lấy đuôi của file ảnh
    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));

    }
    public void onBackButtonClick(View view) {
        onBackPressed();
    }
    private boolean isValidData() {
        String codeValue = MaHS.getText().toString();
        String nameValue = edtName.getText().toString();
        String classValue = edtClass.getText().toString();
        String birthValue = edtBirth.getText().toString();
        String DTBValue = edtscoreDTB.getText().toString();
        String HanhKiemValue = edtscoreHanhKiem.getText().toString();
        String HocLucValue = edtscoreHocLuc.getText().toString();


        // Kiểm tra điều kiện cho từng trường
        if (isStudentCodeExists(codeValue)) {
            Toast.makeText(this, "Mã học sinh đã tồn tại", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (codeValue.isEmpty() || nameValue.isEmpty() || classValue.isEmpty() || birthValue.isEmpty() || DTBValue.isEmpty() || HanhKiemValue.isEmpty() || HocLucValue.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra điều kiện cho trường codeValue (mã học sinh chỉ chứa số)
        if (!codeValue.matches("\\d+")) {
            Toast.makeText(this, "Mã học sinh chỉ được nhập số", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra điều kiện cho trường nameValue (tên chỉ chứa chữ)
        if (!nameValue.matches("[a-zA-Z\\s]+")) {
            Toast.makeText(this, "Tên chỉ được nhập chữ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!HanhKiemValue.matches("[a-zA-Z\\s]+")) {
            Toast.makeText(this, "Lỗi nhập hạnh kiểm", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!HocLucValue.matches("[a-zA-Z\\s]+")) {
            Toast.makeText(this, "Lỗi nhập học lực", Toast.LENGTH_SHORT).show();
            return false;
        }
        double dtb = Double.parseDouble(DTBValue);
        if (dtb < 1 || dtb > 10) {
            Toast.makeText(this, "Điểm trung bình phải từ 1 đến 10", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private boolean isStudentCodeExists(String codeValue) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Student");
        Query query = myRef.orderByChild("code").equalTo(codeValue);
        try {
            DataSnapshot dataSnapshot = Tasks.await(query.get());
            return dataSnapshot.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}