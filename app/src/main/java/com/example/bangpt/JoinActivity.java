package com.example.bangpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JoinActivity extends AppCompatActivity {
    private Button btn_chkID, btn_confirm, btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        btn_chkID=findViewById(R.id.btn_chkID);
        btn_confirm=findViewById(R.id.btn_confirm);
        btn_register=findViewById(R.id.btn_register);
        /*
        //아이디 확인 버튼을 클릭 시 수행
        btn_chkID.setOnClickListener(new View.OnClickListener(){
            @Override
            //버튼을 누르면 아이디 체크
            public void onClick(View view){
                Intent intent=new Intent(LoginActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });*/
        // 트레이너 인증하기 버튼 누르면 수행
        btn_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            //버튼을 누르면 트레이너 인증 화면으로
            public void onClick(View view){
                Intent intent=new Intent(JoinActivity.this,Join2Activity.class);
                startActivity(intent);
            }
        });
        // 레지스터 버튼 클릭시 수행
        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            //회원 가입 후 메인화면으로
            public void onClick(View view){
                Intent intent=new Intent(JoinActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}