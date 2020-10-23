package com.vnat.chatwithfirebase.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.vnat.chatwithfirebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.imgAvatar)
    ImageView edtEmail;

    @BindView(R.id.edtName)
    EditText edtName;

    @BindView(R.id.btnSave)
    Button btnSave;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        init();
        event();

    }

    private void event() {
        edtName.setText(auth.getCurrentUser().getDisplayName());
    }

    private void init() {
        auth = FirebaseAuth.getInstance();
    }
}