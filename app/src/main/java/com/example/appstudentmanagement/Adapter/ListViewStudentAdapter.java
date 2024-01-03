package com.example.appstudentmanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appstudentmanagement.R;
import com.example.appstudentmanagement.Variable.Student;
import com.example.appstudentmanagement.StudentDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListViewStudentAdapter extends ArrayAdapter<Student> {
    private final Context mContext;
    private final List<Student> studentList;

    public ListViewStudentAdapter(Context context, List<Student> list) {
        super(context, 0, list);
        mContext = context;
        studentList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_list_item_view_student, parent, false);
        }

        final Student currentStudent = studentList.get(position);

        ImageView imageView = listItem.findViewById(R.id.student_image);
        TextView MaHSTextView = listItem.findViewById(R.id.student_code);
        TextView nameTextView = listItem.findViewById(R.id.student_name);
        TextView classTextView = listItem.findViewById(R.id.student_class);

        //khai báo biến function
        View showDetailButton = listItem.findViewById(R.id.btn_ShowDetailStudent);

        MaHSTextView.setText(currentStudent.getCode());
        nameTextView.setText(currentStudent.getName());
        classTextView.setText(currentStudent.getStudentClass());
        Picasso.get().load(currentStudent.getPhotoUrl()).into(imageView);
        showDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStudentDetail(currentStudent);
            }
        });

        return listItem;
    }
        private void showStudentDetail (Student student){
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
    }
