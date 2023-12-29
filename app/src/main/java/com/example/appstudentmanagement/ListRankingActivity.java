package com.example.appstudentmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    private ListRankingAdapter adapter;  // Sửa tên adapter thành ListRankingAdapter
    private List<Student> studentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ranking);
        // Khởi tạo ListView
        listView = findViewById(R.id.list_view_ranking);
        studentList = new ArrayList<>();
        adapter = new ListRankingAdapter(this, studentList);  // Sửa tên adapter thành ListRankingAdapter
        listView.setAdapter(adapter);
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
                // Sắp xếp danh sách theo điểm trung bình
                Collections.sort(studentList, new Comparator<Student>() {
                    @Override
                    public int compare(Student s1, Student s2) {
                        // Add a check for empty or null strings before parsing
                        if (s1.getScoreDTB() != null && !s1.getScoreDTB().isEmpty() &&
                                s2.getScoreDTB() != null && !s2.getScoreDTB().isEmpty()) {
                            return Double.compare(Double.parseDouble(s2.getScoreDTB()), Double.parseDouble(s1.getScoreDTB()));
                        } else {
                            // Handle the case where scoreDTB is empty or null
                            return 0; // or any other appropriate handling
                        }
                    }
                });
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi không thể đọc dữ liệu từ Firebase
            }
        });
    }
}
