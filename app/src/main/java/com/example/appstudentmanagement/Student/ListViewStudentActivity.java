package com.example.appstudentmanagement.Student;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appstudentmanagement.Adapter.ListViewStudentAdapter;
import com.example.appstudentmanagement.Adapter.StudentListAdapter;
import com.example.appstudentmanagement.R;
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

public class ListViewStudentActivity extends AppCompatActivity {
    private ListView listView;
    private ListViewStudentAdapter adapter;
    private List<Student> studentList;
    private List<Student> originalStudentList;
    private List<Student> filteredStudentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_student); // Sửa lỗi chính tả ở đây

        listView = findViewById(R.id.lv_students);
        studentList = new ArrayList<>();
        adapter = new ListViewStudentAdapter(this, studentList);
        listView.setAdapter(adapter);
        originalStudentList = new ArrayList<>();
        filteredStudentList = new ArrayList<>();
        adapter = new ListViewStudentAdapter(this, filteredStudentList);
        listView.setAdapter(adapter);
        // Tìm kiếm
        EditText edtSearch = findViewById(R.id.edt_search);
        Button btnSearch = findViewById(R.id.btn_search);

        Spinner spinnerSortOptions = findViewById(R.id.spinner_sortOptions);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortOptions.setAdapter(adapter);
        spinnerSortOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = (String) parent.getItemAtPosition(position);
                switch (selectedOption) {
                    case "Tên":
                        sortByName();
                        break;
                    case "MãHS":
                        sortByStudentID();
                        break;
                    case "Lớp":
                        sortByClass();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không cần xử lý gì khi không có tùy chọn nào được chọn
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = edtSearch.getText().toString().trim().toLowerCase();
                filterStudents(query);
            }
        });


        // Nút BackHome
        ImageButton backButton = findViewById(R.id.btn_back_lvStudent);
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
                originalStudentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Student student = postSnapshot.getValue(Student.class);
                    studentList.add(student);
                    originalStudentList.add(student);
                }
                filterStudents("");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi không thể đọc dữ liệu từ Firebase
            }
        });
    }
    private void searchStudents(String query) {
        filteredStudentList.clear();

        for (Student student : originalStudentList) {
            if (student.getName().toLowerCase().contains(query)) {
                filteredStudentList.add(student);
            }
        }

        adapter.notifyDataSetChanged();
    }
    private void filterStudents(String query) {
        filteredStudentList.clear();

        for (Student student : originalStudentList) {
            if (student.getName().toLowerCase().contains(query) || student.getCode().toLowerCase().contains(query) ) {
                filteredStudentList.add(student);
            }
        }

        adapter.notifyDataSetChanged();
    }
    private void sortByName() {
        Collections.sort(filteredStudentList, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortByStudentID() {
        Collections.sort(filteredStudentList, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getCode().compareTo(s2.getCode());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortByClass() {
        Collections.sort(filteredStudentList, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                String className1 = s1.getClass().getName();
                String className2 = s2.getClass().getName();
                return className1.compareTo(className2);
            }
        });
        adapter.notifyDataSetChanged();
    }
}
