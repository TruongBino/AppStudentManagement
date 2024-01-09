package com.example.appstudentmanagement.Admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appstudentmanagement.Adapter.StudentAccountAdapter;
import com.example.appstudentmanagement.R;
import com.example.appstudentmanagement.Variable.StudentAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListAccountStudentActivity extends AppCompatActivity {

    private ListView listView;
    private StudentAccountAdapter adapter;
    private List<StudentAccount> userList;
    private List<StudentAccount> selectedAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listacc_students);
        Button btnDelete = findViewById(R.id.btn_Delete);
        ImageButton backButton = findViewById(R.id.btn_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        listView = findViewById(R.id.lv_studentAccountListView);
        userList = new ArrayList<>();
        selectedAccounts = new ArrayList<>();
        adapter = new StudentAccountAdapter(this, userList);
        listView.setAdapter(adapter);
        loadUserList();

        adapter.setOnAccountCheckedChangeListener(new StudentAccountAdapter.OnAccountCheckedChangeListener() {
            @Override
            public void onAccountCheckedChange(StudentAccount account, boolean isChecked) {
                if (isChecked) {
                    selectedAccounts.add(account);
                } else {
                    selectedAccounts.remove(account);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedAccounts();
            }
        });
    }

    private void loadUserList() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");

        Query query = databaseRef.orderByChild("role").equalTo("student");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userEmail = snapshot.child("email").getValue(String.class);
                    StudentAccount studentAccount = new StudentAccount(userEmail);
                    userList.add(studentAccount);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi không thể lấy danh sách người dùng
                String errorMessage = databaseError.getMessage();
                Log.e("Firebase Error", "Error deleting account: " + errorMessage);
                // Xử lý lỗi tại đây
            }
        });
    }

    private void deleteSelectedAccounts() {
        for (StudentAccount account : selectedAccounts) {
            deleteAccount(account);
        }
    }

    private void deleteAccount(StudentAccount studentAccount) {
        String userEmail = studentAccount.getEmail();

        // Xóa tài khoản từ Firebase Authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Xóa tài khoản từ Firebase Authentication thành công
                                deleteAccountFromDatabase(userEmail);
                            } else {
                                // Xóa tài khoản từ Firebase Authentication không thành công
                                String errorMessage = task.getException().getMessage();
                                Log.e("Firebase Error", "Error deleting account: " + errorMessage);
                            }
                        }
                    });
        }
    }

    private void deleteAccountFromDatabase(String userEmail) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
        Query query = databaseRef.orderByChild("email").equalTo(userEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
                loadUserList(); // Reload danh sách sau khi xóa tài khoản
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi không thể xóa tài khoản từ Realtime Database
            }
        });
    }
}