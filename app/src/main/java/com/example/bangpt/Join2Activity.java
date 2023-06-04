package com.example.bangpt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.bangpt.Request.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;

public class Join2Activity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;
    private Uri selectedImagePath;

    private String imsi_string;
    private RequestQueue requestQueue;
    private static final String url = "http://10.0.2.2:821/trainers/get-photo";
    private Button btn_register;

    private ImageView imageView; //지우기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);

        btn_register=findViewById(R.id.btn_register);
        imageView = (ImageView) findViewById(R.id.imageView); //지우기

        requestQueue = Volley.newRequestQueue(this);
        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            //회원 가입 후 메인화면으로
            public void onClick(View view){
                // 나중에 사진 업로드 됐을 때만 메인액티비티로 가도록 만들어주기
                Intent intent=new Intent(Join2Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Button galleryButton = findViewById(R.id.btn_upload);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchPickPictureIntent();
            }
        });
    }
    //1-1
    private void dispatchPickPictureIntent() {
        /*
        Intent pickPictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
        //Intent pickPictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPictureIntent.setType("image/*");
        if (pickPictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pickPictureIntent, REQUEST_IMAGE_PICK);
        }

         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            selectedImagePath = getRealPathFromURI(selectedImageUri);
            try {
                uploadImage(selectedImagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            // 이건 저장된 이미지 띄우기
            // imageView.setImageURI(selectedImageUri);

            try {
                uploadImage(selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private Uri getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            return Uri.parse(uri.getPath());
        } else {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            String path = cursor.getString(columnIndex);
            cursor.close();
            return Uri.parse(path);
        }
    }

    private void uploadImage(Uri imageUri) throws IOException {
        Uri filePath = getRealPathFromURI(imageUri);
        String file_path = filePath.toString();
        File imageFile = new File(file_path);

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(
                Request.Method.POST,url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d("Upload", "Image uploaded successfully");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Upload", "Image upload failed: " + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                String uriString = getRealPathFromURI(imageUri).toString();
                params.put("image", new DataPart(imageFile.getName(), uriString.getBytes()));
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        requestQueue.add(volleyMultipartRequest);
    }
}