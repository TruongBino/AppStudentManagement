package com.example.appstudentmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appstudentmanagement.R;
import com.example.appstudentmanagement.Variable.TeacherAccount;

import java.util.List;

public class TeacherAccountAdapter extends ArrayAdapter<TeacherAccount> {
    // Constructor
    public TeacherAccountAdapter(Context context, List<TeacherAccount> teacherAccountList) {
        super(context, 0, teacherAccountList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_acc_teacher, parent, false);
        }

        // Get the current TeacherAccount object
        TeacherAccount currentTeacher = getItem(position);

        // Set the data to views in the layout
        TextView teacherNameTextView = listItemView.findViewById(R.id.tv_Account_Name);
        teacherNameTextView.setText(currentTeacher.getUsername());

        TextView teacherEmailTextView = listItemView.findViewById(R.id.tv_Account_Password);
        teacherEmailTextView.setText(currentTeacher.getPassword());

        return listItemView;
    }
}
