package com.example.appstudentmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListExamActivity extends AppCompatActivity {
    private Button btnAddExam;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_assignment_layout);
        FirebaseAuth.getInstance().signOut();

        context = this; // Lưu context của activity

        // Trong onCreate của activity
        ListView assignmentListView = findViewById(R.id.teacherAccountListView);

        // Lấy danh sách bài tập từ Firebase Realtime Database và thiết lập adapter
        // Khởi tạo đối tượng DatabaseReference để truy cập đến node "assignments"
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("assignments");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Assignment> assignments = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Assignment assignment = snapshot.getValue(Assignment.class);
                    assignments.add(assignment);
                }
                AssignmentAdapter adapter = new AssignmentAdapter(context, assignments); // Sử dụng danh sách assignments
                assignmentListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });

        initUI();
        btnAddExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ListExamActivity.this, CreateAssignmentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initUI() {
        btnAddExam = findViewById(R.id.btn_addExam);
    }
}