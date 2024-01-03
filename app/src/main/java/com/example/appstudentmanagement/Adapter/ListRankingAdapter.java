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
import com.example.appstudentmanagement.Variable.Student;

import java.util.List;

public class ListRankingAdapter  extends ArrayAdapter<Student> {
    private final Context mContext;
    private final List<Student> studentList;

    public ListRankingAdapter(Context context, List<Student> list) {
        super(context, 0, list);
        mContext = context;
        studentList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_item_list_ranking, parent, false);
        }

        final Student currentStudent = studentList.get(position);

        TextView rankTextView = listItem.findViewById(R.id.textViewRank);
        TextView nameTextView = listItem.findViewById(R.id.textViewName);
        TextView dtbTextView = listItem.findViewById(R.id.textViewScore);


        rankTextView.setText("Rank " + (position + 1));
        nameTextView.setText(currentStudent.getName());
        dtbTextView.setText(currentStudent.getScoreDTB());
        return listItem;
    }
}
