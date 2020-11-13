package com.vnat.chatwithfirebase.Message.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vnat.chatwithfirebase.Message.Adapter.MessageAdapter;
import com.vnat.chatwithfirebase.Message.Model.Message;
import com.vnat.chatwithfirebase.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends AppCompatActivity {
    @BindView(R.id.rcvMessage)
    RecyclerView rcvMessage;

    @BindView(R.id.edtInputText)
    EditText edtInputText;

    @BindView(R.id.imgSend)
    ImageView imgSend;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txtFullName)
    TextView txtFullName;

    private String uid = "";
    private String fullName = "";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;

    private List<Message> messageList;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ButterKnife.bind(this);

        init();
        funGetIntentUserFriend();
        actionToolbar();
        funClickSend();
        funGetDataMessage();
    }

    private void funGetDataMessage() {
        mReference.child("Chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Message message = dataSnapshot.getValue(Message.class);

                    if (message.getSender().equals(mUser.getUid()) && message.getReceiver().equals(uid) ||
                            message.getReceiver().equals(mUser.getUid()) && message.getSender().equals(uid)){

                        messageList.add(message);

                    }
                    messageAdapter = new MessageAdapter(messageList);
                    rcvMessage.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessageActivity.this, error.toException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.getNavigationIcon().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtFullName.setText(fullName);

    }

    private void funGetIntentUserFriend() {
        uid = getIntent().getStringExtra("uid");
        fullName = getIntent().getStringExtra("fullName");
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mReference = FirebaseDatabase.getInstance().getReference();

        rcvMessage.setHasFixedSize(true);
        messageList = new ArrayList<>();
    }

    private void funClickSend() {
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edtInputText.getText().toString();
                if (message.isEmpty()){
                    Toast.makeText(MessageActivity.this, "Please input text", Toast.LENGTH_SHORT).show();
                }else{
                    mReference.child("Chats").push().setValue(new Message(message, mUser.getUid(), uid));
                    edtInputText.setText("");
                }

            }
        });

    }

}