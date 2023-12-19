package com.example.appstudentmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class ActivityAddStudent extends AppCompatActivity {
    private ProgressDialog progressDialog;
    ImageView upLoadStudentImg;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private EditText MaHS,edtName,edtBirth,edtClass,edtSDT,edtAddress,edtDetail;
    private EditText edtscoreToan, edtscoreVan,edtscoreAnh,edtscoreVatLy,edtscoreLichSu
            ,edtscoreDialy,edtscoreGDCD,edtscoreHoa, edtscoreSinh,edtscoreHanhKiem,edtscoreDTB,edtscoreHocLuc;
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

        edtscoreToan = findViewById(R.id.et_ScoreToan);
        edtscoreVan = findViewById(R.id.et_ScoreVan);
        edtscoreAnh = findViewById(R.id.et_ScoreAnh);
        edtscoreVatLy = findViewById(R.id.et_ScoreVatLy);
        edtscoreLichSu = findViewById(R.id.et_ScoreLichSu);
        edtscoreDialy = findViewById(R.id.et_ScoreDiaLy);
        edtscoreGDCD = findViewById(R.id.et_ScoreGDCD);
        edtscoreHoa = findViewById(R.id.et_ScoreHoaHoc);
        edtscoreSinh = findViewById(R.id.et_ScoreSinhHoc);
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
                        String toanValue = edtscoreToan.getText().toString();
                        String VanValue = edtscoreVan.getText().toString();
                        String AnhValue = edtscoreAnh.getText().toString();
                        String VatLyValue = edtscoreVatLy.getText().toString();
                        String LichSuValue = edtscoreLichSu.getText().toString();
                        String DialyValue = edtscoreDialy.getText().toString();
                        String GDCDValue = edtscoreGDCD.getText().toString();
                        String HoaValue = edtscoreHoa.getText().toString();
                        String SinhValue = edtscoreSinh.getText().toString();
                        String HanhKiemValue = edtscoreHanhKiem.getText().toString();
                        String DTBValue = edtscoreDTB.getText().toString();
                        String HocLucValue = edtscoreHocLuc.getText().toString();


                        // Tạo đối tượng Student
                        Student student = new Student(photoUrl, codeValue, nameValue, classValue, birthValue, addressValue, phoneValue, detailValue,
                                toanValue, VanValue, AnhValue,VatLyValue,LichSuValue, DialyValue, GDCDValue , HoaValue , SinhValue , HanhKiemValue, DTBValue , HocLucValue);

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

    // Lấy đuôi của file ảnh
    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));

    }
    public void onBackButtonClick(View view) {
        onBackPressed();
    }
}