package com.example.appstudentmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ListStudentActivity extends AppCompatActivity {
    private ListView listView;
    private StudentListAdapter adapter;
    private List<Student> studentList;
    private ImageButton btnAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_listview); // Sửa lỗi chính tả ở đây

        listView = findViewById(R.id.list_view_students);
        studentList = new ArrayList<>();
        adapter = new StudentListAdapter(this, studentList);
        listView.setAdapter(adapter);
        btnAddStudent = findViewById(R.id.btn_AddStudentLV);


        // Nút BackHome
        ImageButton backButton = findViewById(R.id.btn_backHome);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Lấy dữ liệu từ Firebase Realtime Database
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Student");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Student student = postSnapshot.getValue(Student.class);
                    studentList.add(student);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi không thể đọc dữ liệu từ Firebase
            }
        });

        // Kiểm tra vai trò của người dùng và hiển thị button tương ứng
        checkUserRole();
    }

    public void onBackButtonClick(View view) {
        onBackPressed();
    }

    private void checkUserRole() {
        SharedPreferences sharedPref = getSharedPreferences("userRole", Context.MODE_PRIVATE);
        String role = sharedPref.getString("role", "");

        if (role.equals("teacher")) {
            // Nếu là giáo viên, hiển thị button thêm học sinh và xử lý sự kiện khi click
            btnAddStudent.setVisibility(View.VISIBLE);
            btnAddStudent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ListStudentActivity.this, ActivityAddStudent.class);
                    startActivity(intent);
                }
            });
        } else {
            // Nếu không phải là giáo viên, ẩn button thêm học sinh và button xóa học sinh
            btnAddStudent.setVisibility(View.GONE);
        }
    }

}
