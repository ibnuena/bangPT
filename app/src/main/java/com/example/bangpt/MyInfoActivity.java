package com.example.bangpt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MyInfoActivity extends AppCompatActivity {
    private TextView tv_id, tv_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        tv_id = findViewById(R.id.tv_id);
        tv_pass = findViewById(R.id.tv_pass);
        String userID = getIntent().getStringExtra("userID");
        String userPass = getIntent().getStringExtra("userPass");

        tv_id.setText(userID);
        tv_pass.setText(userPass);
    }
}