package com.example.mvvmapp.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mvvmapp.R;

public class HomeActivity extends AppCompatActivity {
    private TextView firstName;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firstName = findViewById(R.id.inFirstName);
        profileImage = findViewById(R.id.ivProfileImage);

        Intent i = getIntent();
        String mFirstname = i.getStringExtra("firstName");
        String mEmail = i.getStringExtra("email");
        String mPass = i.getStringExtra("password");
        String imageName = i.getStringExtra("ImageName");
        String mProfileImagePath = getApplicationInfo().dataDir + "/profile_images/" + imageName + ".png";
        Log.d("PathToImage", mProfileImagePath);
        firstName.setText("Hello, " + mFirstname);
        profileImage.setImageURI(Uri.parse(mProfileImagePath));

    }

}
