package com.example.bangpt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CalendarMemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_memo);

        // 인텐트로 전달한 데이터를 가져옵니다.
        int year = getIntent().getIntExtra("year", 0);
        int month = getIntent().getIntExtra("month", 0);
        int day = getIntent().getIntExtra("day", 0);
    }
}