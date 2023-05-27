package com.example.bangpt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class CalendarMemoActivity extends AppCompatActivity {
    private ArrayList<String> itemList;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_memo);

        // 인텐트로 전달한 데이터를 가져옵니다.
        int year = getIntent().getIntExtra("year", 0);
        int month = getIntent().getIntExtra("month", 0);
        int day = getIntent().getIntExtra("day", 0);

        itemList = new ArrayList<>();

        // XML에서 ListView, EditText, Button을 찾아 연결합니다.
        listView = findViewById(R.id.my_list2);
        editText = findViewById(R.id.write);
        button = findViewById(R.id.reg_button);

        // ListView에 사용될 어댑터를 생성합니다.
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);

        // ListView에 어댑터를 설정합니다.
        listView.setAdapter(adapter);

        // 등록 버튼 클릭 시 이벤트 리스너를 설정합니다.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText에서 입력한 텍스트를 가져옵니다.
                String inputText = editText.getText().toString();

                // 가져온 텍스트를 itemList에 추가합니다.
                itemList.add(inputText);

                // 어댑터에 변경된 데이터를 알립니다.
                adapter.notifyDataSetChanged();

                // EditText의 내용을 초기화합니다.
                editText.setText("");
            }
        });

    }
}