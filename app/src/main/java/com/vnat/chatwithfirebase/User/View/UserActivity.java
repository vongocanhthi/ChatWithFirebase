package com.vnat.chatwithfirebase.User.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vnat.chatwithfirebase.R;
import com.vnat.chatwithfirebase.User.Adapter.UserAdapter;
import com.vnat.chatwithfirebase.User.Model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {
    @BindView(R.id.rcvUser)
    RecyclerView rcvUser;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    List<User> userList;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ButterKnife.bind(this);
        init();

        funDisplayUser();

    }

    private void funDisplayUser() {
        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);

                    if (user != null && !user.getUid().equals(firebaseUser.getUid())) {
                        userList.add(user);
                    }
                }

                userAdapter = new UserAdapter(getApplicationContext(), userList);
                rcvUser.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserActivity.this, error.toException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        userList = new ArrayList<>();
        rcvUser.setHasFixedSize(true);

    }
}