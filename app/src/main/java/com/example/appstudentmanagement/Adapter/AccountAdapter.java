package com.example.appstudentmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appstudentmanagement.Variable.Account;
import com.example.appstudentmanagement.R;

import java.util.List;

public class AccountAdapter extends ArrayAdapter<Account> {

    private Context context;
    private List<Account> accountList;

    public AccountAdapter(Context context, List<Account> accountList) {
        super(context, 0, accountList);
        this.context = context;
        this.accountList = accountList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_acc_student, parent, false);
        }

        Account account = accountList.get(position);

        TextView displayNameTextView = view.findViewById(R.id.tv_Account_Name);
        TextView emailTextView = view.findViewById(R.id.tv_Account_Email);

        displayNameTextView.setText(account.getDisplayName());
        emailTextView.setText(account.getEmail());

        return view;
    }
}