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
import com.example.appstudentmanagement.Variable.StudentAccount;

import java.util.ArrayList;

public class StudentAccountAdapter  extends ArrayAdapter<StudentAccount> {
    private Context context;
    private ArrayList<StudentAccount> studentAccounts;

    public StudentAccountAdapter(@NonNull Context context, ArrayList<StudentAccount> studentAccounts) {
        super(context, 0, studentAccounts);
        this.context = context;
        this.studentAccounts = studentAccounts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_acc_student, parent, false);
        }

        StudentAccount studentAccount = studentAccounts.get(position);

        TextView tvUsername = view.findViewById(R.id.tv_Account_Email);
        TextView tvPassword = view.findViewById(R.id.tv_Account_Password);

        tvUsername.setText(studentAccount.getEmail());
        tvPassword.setText(studentAccount.getPassword());

        return view;
    }
}
