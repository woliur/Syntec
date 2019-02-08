package com.woliur.assignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

public class UserDetailsActivity extends AppCompatActivity {

    ImageView ivPic;
    TextView tvName;
    TextView tvEmail;
    TextView tvAddress;
    TextView tvPhone;
    TextView tvGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        ivPic = findViewById(R.id.iv_pic);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvAddress = findViewById(R.id.tv_address);
        tvPhone = findViewById(R.id.tv_phone);
        tvGender = findViewById(R.id.tv_gender);

        Bundle bundle = getIntent().getExtras();

        String imageString = bundle.getString("pic");
        String name = bundle.getString("name");
        String email = bundle.getString("email");
        String address = bundle.getString("address");
        String phone = bundle.getString("phone");
        String gender = bundle.getString("gender");

//        if (imageString != null) {
//            Bitmap bitmap = getBitmap(imageString);
//            ivPic.setImageBitmap(bitmap);
//        }

            tvName.setText("Name: " + name);
            tvEmail.setText("Email: " + email);
            tvAddress.setText("Address: " + address);
            tvPhone.setText("Phone: " + phone);
            tvGender.setText("Gender: " + gender);
    }

    private Bitmap getBitmap(String imageString) {
        byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
