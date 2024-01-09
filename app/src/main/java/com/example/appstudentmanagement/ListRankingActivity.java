package com.example.appstudentmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appstudentmanagement.Adapter.ListRankingAdapter;
import com.example.appstudentmanagement.Variable.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListRankingActivity extends AppCompatActivity {
    private ListView listView;
    private ListRankingAdapter adapter;
    private List<Student> studentList;
    private List<Student> filteredStudentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ranking);

        listView = findViewById(R.id.lv_ranking);
        studentList = new ArrayList<>();
        filteredStudentList = new ArrayList<>();
        adapter = new ListRankingAdapter(this, filteredStudentList);
        listView.setAdapter(adapter);

        EditText edtSearch = findViewById(R.id.edt_search);
        Button btnSearch = findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = edtSearch.getText().toString().trim();
                filterStudents(query);
            }
        });

        ImageButton backButton = findViewById(R.id.btn_backHome);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Student");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Student student = postSnapshot.getValue(Student.class);
                    studentList.add(student);
                }

                Collections.sort(studentList, new Comparator<Student>() {
                    @Override
                    public int compare(Student s1, Student s2) {
                        if (s1.getScoreDTB() != null && !s1.getScoreDTB().isEmpty() &&
                                s2.getScoreDTB() != null && !s2.getScoreDTB().isEmpty()) {
                            return Double.compare(Double.parseDouble(s2.getScoreDTB()), Double.parseDouble(s1.getScoreDTB()));
                        } else {
                            return 0;
                        }
                    }
                });

                filterStudents(""); // Hiển thị tất cả sinh viên khi ban đầu
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi không thể đọc dữ liệu từ Firebase
            }
        });
    }

    private void filterStudents(String query) {
        filteredStudentList.clear();

        for (Student student : studentList) {
            if (student.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredStudentList.add(student);
            }
        }

        adapter.notifyDataSetChanged();
    }
}