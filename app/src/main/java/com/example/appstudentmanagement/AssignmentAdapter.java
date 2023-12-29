package com.example.appstudentmanagement;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appstudentmanagement.Assignment;
import com.example.appstudentmanagement.R;

import java.io.File;
import java.util.List;

public class AssignmentAdapter extends BaseAdapter {

    private List<Assignment> assignmentList;
    private Context context;

    public AssignmentAdapter(Context context, List<Assignment> assignmentList) {
        this.context = context;
        this.assignmentList = assignmentList;
    }

    @Override
    public int getCount() {
        return assignmentList.size();
    }

    @Override
    public Object getItem(int position) {
        return assignmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment, parent, false);
        }

        TextView subjectTextView = convertView.findViewById(R.id.subjectTextView);
        TextView contentTextView = convertView.findViewById(R.id.contentTextView);
        ImageView mediaImageView = convertView.findViewById(R.id.mediaImageView);

        Assignment assignment = assignmentList.get(position);
        subjectTextView.setText("Môn học: " + assignment.getSubject());
        contentTextView.setText("Nội dung: " + assignment.getContent());

        // Lưu đường dẫn file bài tập vào tag của ImageView
        mediaImageView.setTag(assignment.getMediaUrl());

        // Xử lý sự kiện khi người dùng bấm vào ImageView
        mediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = (String) v.getTag();

                if (filePath != null) {
                    File file = new File(filePath);

                    if (file.exists()) {
                        // Tạo Intent để mở file
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Uri uri = Uri.fromFile(file);
                        intent.setDataAndType(uri, "*/*"); // Thay đổi kiểu file tùy thuộc vào loại file bài tập
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        try {
                            context.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(context, "Không thể mở file", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "File không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return convertView;
    }
}