package com.example.mvvmapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmapp.model.User;
import com.example.mvvmapp.model.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private LiveData<List<User>> users;
    private UserRepository repository;
    public MutableLiveData<String> firstName = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<User> userMutableLiveData;

    private static final String TAG = "UserViewModel";

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        users = repository.getListOfUsers();
    }

    public LiveData<List<User>> getAllUsers(){
        return users;
    }

    public LiveData<User> getUser() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }


    public User getUserById(String email){
        return repository.getUser(email);
    }

    public void addUser(User user){
        Log.d(TAG, "Adding user .... ");
        repository.addUser(user);

    }

    public void updateUserProfileImage(String newImageName, String email){
        Log.d(TAG, "Updating user .... ");
        repository.updateUserProfileImage(newImageName, email);

    }

    public void update(User user){
        repository.updateUser(user);
    }

}



