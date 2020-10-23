package com.vnat.chatwithfirebase.Message.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.vnat.chatwithfirebase.Message.Model.Message;
import com.vnat.chatwithfirebase.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends AppCompatActivity {
    @BindView(R.id.rcvMessage)
    RecyclerView rcvMessage;

    @BindView(R.id.edtInputText)
    EditText edtInputText;

    @BindView(R.id.imgSend)
    ImageView imgSend;

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ButterKnife.bind(this);

        init();
        funClickSend();
        funGetDataMessage();
    }

    private void init() {
        user = FirebaseAuth.getInstance().getCurrentUser();

        rcvMessage.setHasFixedSize(true);
        rcvMessage.setLayoutManager(new LinearLayoutManager(this));

    }

    private void funGetDataMessage() {
//        messageAdapter = new MessageAdapter()
    }

    private void funClickSend() {

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String time = simpleDateFormat.format(new Date().getTime());

                String message = edtInputText.getText().toString();

                FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(time).setValue(
                        new Message(message, user.getDisplayName()));

                edtInputText.setText("");

            }
        });
    }
}