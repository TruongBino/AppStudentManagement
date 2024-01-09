package com.example.appstudentmanagement.Student;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appstudentmanagement.Adapter.AssignmentAdapter;
import com.example.appstudentmanagement.R;
import com.example.appstudentmanagement.Variable.Assignment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentListExam extends AppCompatActivity {
    private List<Assignment> AssignmentList;
    private List<Assignment> originalAssignmentList;
    private List<Assignment> filteredAssignmentList;
    private ListView assignmentListView;
    private AssignmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_assignment_student);
        FirebaseAuth.getInstance().signOut();


        EditText edtSearch = findViewById(R.id.edt_search);
        Button btnSearch = findViewById(R.id.btn_search);

        originalAssignmentList = new ArrayList<>();
        filteredAssignmentList = new ArrayList<>();
        AssignmentList = new ArrayList<>();
        assignmentListView = findViewById(R.id.lv_assignment);
        adapter = new AssignmentAdapter(this, filteredAssignmentList);
        assignmentListView.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = edtSearch.getText().toString().trim().toLowerCase();
                filterAssignments(query);
            }
        });

        // Trong onCreate của activity
        ListView assignmentListView = findViewById(R.id.lv_assignment);

        // Lấy danh sách bài tập từ Firebase Realtime Database và thiết lập adapter
        // Khởi tạo đối tượng DatabaseReference để truy cập đến node "assignments"
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("assignments");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AssignmentList.clear();
                originalAssignmentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Assignment assignment = postSnapshot.getValue(Assignment.class);
                    AssignmentList.add(assignment);
                    originalAssignmentList.add(assignment);
                }
                filterAssignments("");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });
    }
    private void filterAssignments(String query) {
        filteredAssignmentList.clear();

        for (Assignment assignment : originalAssignmentList) {
            if (assignment.getSubject().toLowerCase().contains(query) || assignment.getContent().toLowerCase().contains(query))  {
                filteredAssignmentList.add(assignment);
            }
        }

        adapter.notifyDataSetChanged();
    }
}
