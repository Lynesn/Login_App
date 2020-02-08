package com.example.mvvmapp.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.mvvmapp.R;
import com.example.mvvmapp.databinding.ActivityMainBinding;
import com.example.mvvmapp.model.User;
import com.example.mvvmapp.viewmodels.UserViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private UserViewModel viewModel;
    private String imageName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        binding.goToLogin.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });
        binding.signUpProfileImage.setOnClickListener(view -> selectImage());

        binding.btnAddUser.setOnClickListener(v -> {
            if (binding.inFirstName.getText().toString().isEmpty()) {
                binding.inFirstName.setError("First Name must not be empty!");
            } else if (binding.inEmail.getText().toString().isEmpty()) {
                binding.inEmail.setError("Email must not be empty!");
            } else if (binding.inPassword.getText().toString().isEmpty()) {
                binding.inPassword.setError("Password must not be empty!");

            } else if (!binding.inPassword.getText().toString().isEmpty() &&
                    !binding.inPasswordConf.getText().toString().equals(binding.inPassword.getText().toString())) {
                binding.inPassword.setError("The two passwords do not match!");
            } else {
                String firstName = binding.inFirstName.getText().toString().trim();
                String email = binding.inEmail.getText().toString().trim();
                String password = binding.inPassword.getText().toString().trim();
                User user = new User(firstName, email, password, imageName);
                viewModel.addUser(user);

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                Toast.makeText(getApplicationContext(), "User added successfully", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void showUsers() {
        viewModel.getAllUsers().observe(this, users -> {
            List<User> userslist = viewModel.getAllUsers().getValue();

            Log.d(TAG, "showUsers: " + userslist.toString());


        });
    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri selectedimg = data.getData();
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg);
                binding.signUpProfileImage.setImageBitmap(image);
                imageName = saveImageToShare(image); // this will save the image for the user and return the image file name
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String saveImageToShare(Bitmap image) {
        String imageName = null;
        File imagesFolder = new File(getApplicationInfo().dataDir, "profile_images");
        try {
            if (!imagesFolder.exists()) {
                if (!imagesFolder.mkdirs()) {
                    if (!imagesFolder.mkdirs()) {
                        Log.d(TAG, "Failed to create image folders");
                    }
                }
            }
            imageName = String.valueOf(System.currentTimeMillis());
            File file = new File(imagesFolder, imageName + ".png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageName;
    }
}
