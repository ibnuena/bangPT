package com.example.bangpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
/*
import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
*/
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




public class JoinActivity extends AppCompatActivity {
    private Button btn_chkID, btn_confirm, btn_register;

    private EditText et_id,et_pass, et_name, et_nickname;
    /*
    private static final String SERVER_URL = "http://10.0.2.2:5000/push/";
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
     */
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        client = new OkHttpClient();

        btn_chkID=findViewById(R.id.btn_chkID);
        btn_confirm=findViewById(R.id.btn_confirm);
        btn_register=findViewById(R.id.btn_register);

        et_id=findViewById(R.id.et_id);
        et_pass=findViewById(R.id.et_pass);
        et_name=findViewById(R.id.et_name);
        et_nickname=findViewById(R.id.et_nickname);

        //아이디 확인 버튼을 클릭 시 수행
        btn_chkID.setOnClickListener(new View.OnClickListener(){
            @Override
            //버튼을 누르면 아이디 중복 여부 체크
            public void onClick(View view){
                String user_id = et_id.getText().toString();
                try {
                    JSONObject json = new JSONObject();

                    json.put("id", user_id);

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString());

                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:5000/chkId/")
                            .post(requestBody)
                            .build();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.newCall(request).enqueue(new Callback() {

                                @Override
                                public void onFailure(Call call, IOException e) {
                                    // 요청 실패 시 처리
                                    Log.e("JoinActivity", "Exception occurred", e);
                                    Log.d("JoinActivity", "중복 확인을 위한 id 전송 실패");
                                }
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    // 요청 성공 시 처리
                                    Log.d("JoinActivity", "중복 확인을 위한 id 전송 성공");
                                    if (response.isSuccessful()) {
                                        try {
                                            String responseData = response.body().string();
                                            JSONObject responseJson = new JSONObject(responseData);
                                            boolean success = responseJson.getBoolean("repeated");
                                            final String message = responseJson.getString("message");

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(JoinActivity.this, message, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        /*
                                            if (success) {
                                                // 회원가입 성공

                                            } else {
                                                // 회원가입 실패
                                                // 여기에서 필요한 실패 처리를 수행하세요.
                                            }
                                        */
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                    }).start();

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });

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
                String user_id = et_id.getText().toString();
                String user_password = et_pass.getText().toString();
                String user_name = et_name.getText().toString();
                String user_nickname = et_nickname.getText().toString();

                registerUser(user_id, user_password, user_name, user_nickname);
            }
                /*
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://10.0.2.2:5000/join/");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");

                            JSONObject dataObject = new JSONObject();

                            try {
                                dataObject.put("name", "정혜연");
                                dataObject.put("nickname", "울겨");

                                String jsonData = dataObject.toString();

                                RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE, jsonData);

                                Request request = new Request.Builder()
                                        .url(url)
                                        .post(requestBody)
                                        .build();

                                Response response = client.newCall(request).execute();
                                if (response.isSuccessful()) {
                                    // 응답이 성공적으로 받아졌을 때의 처리
                                    Log.d("MainActivity", "Data sent successfully");
                                } else {
                                    // 응답이 실패했을 때의 처리
                                    Log.d("MainActivity", "Data sending failed");
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                        }catch(Exception e){
                            //    textView.setText("error");
                            e.printStackTrace();
                        }
                    }
                }).start();
                Intent intent=new Intent(JoinActivity.this,MainActivity.class);
                startActivity(intent);
                */

                //---------------------------------------------------------------------------------------//
                //여기부터 챗gpt 코드
                /*
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                JSONObject dataObject = new JSONObject();
                try {
                    dataObject.put("name", "John");
                    dataObject.put("age", 25);
                    // 필요한 데이터를 추가로 포함시키거나 수정

                    // JSON 데이터를 문자열로 변환
                    String jsonData = dataObject.toString();

                    // POST 요청을 보낼 RequestBody 생성
                    RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE, jsonData);

                    // POST 요청 생성
                    Request request = new Request.Builder()
                            .url(SERVER_URL)
                            .post(requestBody)
                            .build();

                    // 요청 보내고 응답 받기
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        // 응답이 성공적으로 받아졌을 때의 처리
                        Log.d("MainActivity", "Data sent successfully");
                    } else {
                        // 응답이 실패했을 때의 처리
                        Log.d("MainActivity", "Data sending failed");
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                    }
                }).start();

                 */
                //---------------------------------------------------------------------------------------------------------------------

                /*응비가 알려준 블로그 버전..... 일단 보류ㅜ
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String server_url = "http://10.0.2.2:5000/join/";
                            URL url;
                            String response = "";

                            url = new URL(server_url);

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setReadTimeout(15000);
                            conn.setConnectTimeout(15000);
                            conn.setRequestMethod("POST");

                            Uri.Builder builder = new Uri.Builder()
                                    .appendQueryParameter("name", "정혜연")
                                    .appendQueryParameter("nickname", "울겨");
                            String query = builder.build().getEncodedQuery();

                            OutputStream os = conn.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(
                                    new OutputStreamWriter(os, "UTF-8"));
                            writer.write(query);
                            writer.flush();
                            writer.close();
                            os.close();

                            conn.connect();
                            int responseCode=conn.getResponseCode();



                        }catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                */
                /*다른 블로그,, 이것도 보류 ㅜ
                class RegisterRequest extends StringRequest {

                    //서버 URL 설정(php 파일 연동)
                    //final static private String URL = "http://10.0.2.2:5000/join/";
                    private static final String URL = "http://10.0.2.2:5000/join/";
                    private Map<String, String> map;
                    //private Map<String, String>parameters;

                    final String user_name = et_name.getText().toString();
                    final String user_id = et_id.getText().toString();
                    final String user_password = et_pass.getText().toString();
                    final String user_nickname = et_nickname.getText().toString();

                    public RegisterRequest(String user_name, String user_id, String user_password, String user_nickname, Response.Listener<String> listener, Response.ErrorListener errorListener) {
                        super(Method.POST, URL, listener, errorListener);

                        map = new HashMap<>();
                        map.put("name", user_name);
                        map.put("id", user_id);
                        map.put("password", user_password);
                        map.put("nickname", user_nickname);
                    }

                    @Override
                    protected Map<String, String>getParams() throws AuthFailureError {
                        return map;
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return new JSONObject(map).toString().getBytes("utf-8");
                        } catch (UnsupportedEncodingException e) {
                            return super.getBody();
                        }
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                }

                 */

        });
    }//onCreate

    private void registerUser(String user_id, String user_password, String user_name, String user_nickname) {
        /*
        // 서버로 전송할 데이터를 포함한 RequestBody 생성
        RequestBody requestBody = new FormBody.Builder()
                .add("id", userId)
                .add("password", password)
                .add("name", name)
                .add("nickname", nickname)
                .build();
         */
        try {
            // JSONObject를 사용하여 데이터 구성
            JSONObject json = new JSONObject();
            json.put("id", user_id);
            json.put("password", user_password);
            json.put("name", user_name);
            json.put("nickname", user_nickname);

            // JSON 형식의 RequestBody 생성
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString());

            // 서버로 전송할 요청 생성
            Request request = new Request.Builder()
                    .url("http://3.21.247.81:5000/join/")
                    .post(requestBody)
                    .build();

            // 요청을 실행하고 응답을 받는 비동기 처리
            new Thread(new Runnable() {
                @Override
                public void run() {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            // 요청 실패 시 처리
                          //  e.printStackTrace();
                            Log.e("JoinActivity", "Exception occurred", e);
                            Log.d("JoinActivity", "onFailure");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(JoinActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            // 요청 성공 시 처리
                            Log.d("JoinActivity", "연결됨!!");
                            Log.d("JoinActivity", Integer.toString(response.code()));
                            if (response.isSuccessful()) {
                                try {
                                    String responseData = response.body().string();
                                    JSONObject responseJson = new JSONObject(responseData);
                                    boolean success = responseJson.getBoolean("success");
                                    final String message = responseJson.getString("message");

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(JoinActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    if (success) {
                                        // 회원가입 성공
                                        Intent intent=new Intent(JoinActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        // 회원가입 실패
                                        // 여기에서 필요한 실패 처리를 수행하세요.
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }).start();
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}