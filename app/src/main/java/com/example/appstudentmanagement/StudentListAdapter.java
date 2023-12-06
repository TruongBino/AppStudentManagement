package com.example.appstudentmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import java.util.List;

public class StudentListAdapter extends ArrayAdapter<Student> {
    private Context mContext;
    private List<Student> studentList;

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

        Student currentStudent = studentList.get(position);

        ImageView imageView = listItem.findViewById(R.id.student_image);
        TextView MaHSTextView = listItem.findViewById(R.id.student_code);
        TextView nameTextView = listItem.findViewById(R.id.student_name);
        TextView classTextView = listItem.findViewById(R.id.student_class);

        MaHSTextView.setText(currentStudent.getCode());
        nameTextView.setText(currentStudent.getName());
        classTextView.setText(currentStudent.getStudentClass());
        Picasso.get().load(currentStudent.getPhotoUrl()).into(imageView);

        return listItem;
    }
}
