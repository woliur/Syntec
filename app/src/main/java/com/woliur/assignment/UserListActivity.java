package com.woliur.assignment;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserListActivity extends AppCompatActivity {

    public static final String url = "http://syntecinternapi.azurewebsites.net/api/user/getusers";

    OkHttpClient client = new OkHttpClient();

    ArrayList<User> users = new ArrayList<>();
    UserListAdapter adapter;
    RecyclerView rvUserList;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        adapter = new UserListAdapter(UserListActivity.this, users);

        rvUserList = findViewById(R.id.rv_user_list);
        progressBar = findViewById(R.id.progress_bar);

        rvUserList.setHasFixedSize(true);
        rvUserList.setLayoutManager(new LinearLayoutManager(this));

        rvUserList.setAdapter(adapter);

        fetchUserList();
    }

    private void fetchUserList() {
        final Request request = new Request.Builder()
                .url(url)
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

                                JSONObject object = new JSONObject(result);
                                JSONArray data = object.getJSONArray("Data");

                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject userJson = data.getJSONObject(i);

                                    String pic = userJson.getString("ProfilePic");
                                    String name = userJson.getString("Name");
                                    String email = userJson.getString("Email");
                                    String address = userJson.getString("Address");
                                    String phone = userJson.getString("Phone");
                                    String gender = userJson.getString("Gender");

                                    User user = new User(pic, name, address, email, phone, gender);

                                    users.add(user);
                                }

                                adapter.notifyDataSetChanged();

                            } catch (IOException e) {
                                showResponseDialog("Exception occurred");
                                e.printStackTrace();
                            } catch (JSONException e) {
                                showResponseDialog("Json Error");
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    private void showResponseDialog(String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserListActivity.this);

        builder.setTitle("Response");
        builder.setMessage(result);

        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
