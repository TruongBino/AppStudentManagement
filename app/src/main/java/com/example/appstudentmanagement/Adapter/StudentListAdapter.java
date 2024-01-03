package com.example.appstudentmanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appstudentmanagement.Teacher.EditStudentActivity;
import com.example.appstudentmanagement.R;
import com.example.appstudentmanagement.Variable.Student;
import com.example.appstudentmanagement.StudentDetailsActivity;
import com.example.appstudentmanagement.Teacher.UpdatePointActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StudentListAdapter extends ArrayAdapter<Student> {
    private final Context mContext;
    private final List<Student> studentList;

    public StudentListAdapter(Context context, List<Student> list) {
        super(context, 0, list);
        mContext = context;
        studentList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.student_list_item, parent, false);
        }

        final Student currentStudent = studentList.get(position);

        ImageView imageView = listItem.findViewById(R.id.student_image);
        TextView MaHSTextView = listItem.findViewById(R.id.student_code);
        TextView nameTextView = listItem.findViewById(R.id.student_name);
        TextView classTextView = listItem.findViewById(R.id.student_class);

        //khai báo biến function
        View deleteButton = listItem.findViewById(R.id.btn_DeleteStudent);
        View showDetailButton = listItem.findViewById(R.id.btn_ShowDetailStudent);
        View editButton = listItem.findViewById(R.id.btn_EditStudent);
        View updatePoint = listItem.findViewById(R.id.btn_UpdatePoint);

        MaHSTextView.setText(currentStudent.getCode());
        nameTextView.setText(currentStudent.getName());
        classTextView.setText(currentStudent.getStudentClass());
        Picasso.get().load(currentStudent.getPhotoUrl()).into(imageView);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent(currentStudent);
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStudentDetail(currentStudent);
            }
        });
        updatePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePoint(currentStudent);
            }
        });
        showDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStudentDetail(currentStudent);
            }
        });

        return listItem;
    }

    private void deleteStudent(Student student) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Student");
        myRef.child(student.getCode()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Xóa thành công trên Firebase, sau đó mới xóa khỏi danh sách local và cập nhật giao diện
                    studentList.remove(student);
                    notifyDataSetChanged();
                } else {
                    Log.e("StudentListAdapter", "Lỗi không thể xóa học sinh từ Firebase: " + task.getException());
                }
            }
        });
    }

    private void showStudentDetail(Student student) {
        notifyDataSetChanged();
        Intent intent = new Intent(mContext, StudentDetailsActivity.class);
        intent.putExtra("studentMaHS", student.getCode());
        intent.putExtra("studentName", student.getName());
        intent.putExtra("studentClass", student.getStudentClass());
        intent.putExtra("studentBirth", student.getDateOfBirth());
        intent.putExtra("studentAddress", student.getAddress());
        intent.putExtra("studentSDT", student.getPhone());
        intent.putExtra("studentDetail", student.getDetail());
        intent.putExtra("studentConduct", student.getScoreHanhKiem());
        intent.putExtra("studentGPA", student.getScoreDTB());
        intent.putExtra("studentAcademicAbility", student.getScoreHocLuc());
        intent.putExtra("studentImageResId", R.drawable.man);


        mContext.startActivity(intent);
    }

    private void editStudentDetail(Student student) {
        Intent intent = new Intent(mContext, EditStudentActivity.class);
        intent.putExtra("studentMaHS", student.getCode());
        intent.putExtra("studentName", student.getName());
        intent.putExtra("studentClass", student.getStudentClass());
        intent.putExtra("studentBirth", student.getDateOfBirth());
        intent.putExtra("studentAddress", student.getAddress());
        intent.putExtra("studentSDT", student.getPhone());
        intent.putExtra("studentDetail", student.getDetail());
        intent.putExtra("studentImageResId", R.drawable.man);

        mContext.startActivity(intent);
        notifyDataSetChanged();
    }

    private void updatePoint(Student student) {
        Intent intent = new Intent(mContext, UpdatePointActivity.class);
        intent.putExtra("studentMaHS", student.getCode());
        intent.putExtra("studentConduct", student.getScoreHanhKiem());
        intent.putExtra("studentGPA", student.getScoreDTB());
        intent.putExtra("studentAcademicAbility", student.getScoreHocLuc());
        mContext.startActivity(intent);
        notifyDataSetChanged();
    }
}
