package com.woliur.assignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String url = "http://syntecinternapi.azurewebsites.net/api/user/CreateNew";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    Spinner spinner;
    Button submit;
    Button showUserList;
    Button camera;
    ImageView imageView;
    TextView genderTextView;
    EditText name;
    EditText email;
    EditText address;
    EditText phone;
    ProgressBar progressBar;

    Bitmap bitmap;
    String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        submit = findViewById(R.id.btn_submit);
        showUserList = findViewById(R.id.btn_show_user_list);

        camera = findViewById(R.id.btn_photo);
        imageView = findViewById(R.id.img_profile_pic);
        progressBar = findViewById(R.id.progress_bar);

        genderTextView = findViewById(R.id.tv_spinner_gender);

        name = findViewById(R.id.et_Name);
        email = findViewById(R.id.et_Email);
        address = findViewById(R.id.et_Address);
        phone = findViewById(R.id.et_Phone);

        final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                postData();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);
            }
        });

        showUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserListActivity.class));
            }
        });
    }

    private void postData() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("Name", name);
            postData.put("Email", email);
            postData.put("Address", address);
            postData.put("Phone", phone);
            postData.put("Gender", spinner.getSelectedItem().toString());
            postData.put("ProfilePic", encodedImage);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, postData.toString());

        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        showResponseDialog("Failed to connect to the server!");
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);

                        if (response.body() != null) {
                            String result = null;
                            try {
                                result = response.body().string();
                                showResponseDialog(result);
                            } catch (IOException e) {
                                showResponseDialog("Exception occurred");
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

    }

    private void showResponseDialog(String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Response");
        builder.setMessage(result);

        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);

        encodedImage = getBase64(bitmap);
    }

    private String getBase64(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
