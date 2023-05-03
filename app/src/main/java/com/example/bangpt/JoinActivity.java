package com.example.bangpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        btn_join=findViewById(R.id.btn_join);
        btn_login=findViewById(R.id.btn_login);

        //회원가입 버튼을 클릭 시 수행
        btn_join.setOnClickListener(new View.OnClickListener(){
            @Override
            //버튼을 누르면 JoinActivity로 넘어가게 해준다
            public void onClick(View view){
                Intent intent=new Intent(LoginActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });
    }
}