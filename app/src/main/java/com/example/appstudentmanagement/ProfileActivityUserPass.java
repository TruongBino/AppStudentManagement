package com.example.appstudentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivityUserPass  extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private TextView tvName;
    private EditText etUsername;

    private EditText edtEmail, edtName;
    private Button btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_user_password);

        tvName = findViewById(R.id.tv_User); // Assuming edt_User is the ID of the TextView in your layout

        String teacherName = getIntent().getStringExtra("username");

        if (teacherName != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("teachers").child(teacherName);

            // Check if mDatabase is not null before adding the listener
            if (mDatabase != null) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String username = dataSnapshot.child("username").getValue(String.class);

                            // Hiển thị thông tin đăng nhập trong layout profile
                            tvName.setText("Username: " + username);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Xử lý lỗi khi không lấy được thông tin từ cơ sở dữ liệu
                    }
                });
            } else {
                // Handle the case where mDatabase is null
            }
        } else {
            // Xử lý trường hợp teacherId là null
        }
    }
}

