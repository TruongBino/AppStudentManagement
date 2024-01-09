package com.example.appstudentmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.appstudentmanagement.R;
import com.example.appstudentmanagement.Variable.StudentAccount;

import java.util.ArrayList;
import java.util.List;

public class StudentAccountAdapter extends BaseAdapter {

    private Context context;
    private List<StudentAccount> studentAccounts;
    private List<StudentAccount> selectedAccounts;
    private OnAccountCheckedChangeListener onAccountCheckedChangeListener;

    public StudentAccountAdapter(Context context, List<StudentAccount> studentAccounts) {
        this.context = context;
        this.studentAccounts = studentAccounts;
        this.selectedAccounts = new ArrayList<>();
    }

    public void setOnAccountCheckedChangeListener(OnAccountCheckedChangeListener listener) {
        this.onAccountCheckedChangeListener = listener;
    }

    public List<StudentAccount> getSelectedAccounts() {
        return selectedAccounts;
    }

    @Override
    public int getCount() {
        return studentAccounts.size();
    }

    @Override
    public Object getItem(int position) {
        return studentAccounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_acc_student, null);
        }

        TextView txtEmail = convertView.findViewById(R.id.tv_Email);
        CheckBox checkBox = convertView.findViewById(R.id.cb_Delete);

        final StudentAccount studentAccount = studentAccounts.get(position);

        txtEmail.setText(studentAccount.getEmail());

        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(selectedAccounts.contains(studentAccount));

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedAccounts.add(studentAccount);
                } else {
                    selectedAccounts.remove(studentAccount);
                }
                if (onAccountCheckedChangeListener != null) {
                    onAccountCheckedChangeListener.onAccountCheckedChange(studentAccount, isChecked);
                }
            }
        });

        return convertView;
    }

    public interface OnAccountCheckedChangeListener {
        void onAccountCheckedChange(StudentAccount account, boolean isChecked);
    }
}