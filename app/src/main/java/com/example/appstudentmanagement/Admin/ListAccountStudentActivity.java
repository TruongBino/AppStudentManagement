package com.example.appstudentmanagement.Admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appstudentmanagement.Variable.Account;
import com.example.appstudentmanagement.Adapter.AccountAdapter;
import com.example.appstudentmanagement.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListAccountStudentActivity extends AppCompatActivity {

    private ListView accountListView;
    private ArrayAdapter<Account> adapter;
    private DatabaseReference usersRef;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listacc_students);
        backButton=findViewById(R.id.btn_Back);
        backButton.setOnClickListener(v -> finish());

        accountListView = findViewById(R.id.studentAccountListView);

        usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Account> accountList = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String displayName = userSnapshot.child("displayName").getValue(String.class);
                    String email = userSnapshot.child("email").getValue(String.class);
                    Account account = new Account(displayName, email);
                    accountList.add(account);
                }
                displayUserList(accountList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi cơ sở dữ liệu
            }
        });
    }

    private void displayUserList(List<Account> accountList) {
        adapter = new AccountAdapter(this, accountList);
        accountListView.setAdapter(adapter);
    }
}
