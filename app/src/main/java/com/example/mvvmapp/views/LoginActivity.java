package com.example.mvvmapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.mvvmapp.R;
import com.example.mvvmapp.model.User;
import com.example.mvvmapp.viewmodels.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText email, password;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    public void onLogin(View view) {
        if (email.getText().toString().isEmpty()) {
            email.setError("Email must not be empty!");
        } else if (password.getText().toString().isEmpty()) {
            password.setError("Password must not be empty!");
        } else {

            User user = viewModel.getUserById(email.getText().toString());
            if (user == null) {
                Log.d(TAG, "Wrong credentials ");
                Toast.makeText(getApplicationContext(), "Email doesn't exist", Toast.LENGTH_SHORT).show();
            } else {
                if (user.getPassword().equals(password.getText().toString())) {
                    Intent user_intent = new Intent(getApplicationContext(), HomeActivity.class);
                    user_intent.putExtra("firstName", user.getFirstName());
                    user_intent.putExtra("email", user.getEmail());
                    user_intent.putExtra("password", user.getPassword());
                    user_intent.putExtra("ImageName", user.getProfileImageName());
                    startActivity(user_intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong password, Please try again", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}
