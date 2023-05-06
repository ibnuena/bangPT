package com.example.bangpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CommuQuestionActivity extends AppCompatActivity {
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commu_question);

        button1=findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            //버튼을 누르면 WriteActivity로 넘어가게 해준다
            public void onClick(View view){
                Intent intent=new Intent(CommuQuestionActivity.this,WriteActivity.class);
                startActivity(intent);
            }
        });
    }
}