package com.example.mvvmapp.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {
    private final UserDao userDao;
    private UserDatabase database;
    private String firstName;

    public UserRepository(Application application){
        database = UserDatabase.getDatabase(application);
        userDao = database.userDao();

    }

    private void addDummyUser() {
        User me = new User("Lyne", "lynealex@gmail.com", "123456", "");
        userDao.insertNewUser(me);
    }


    public LiveData<List<User>> getListOfUsers(){
        return userDao.getUsers();
    }

    public void addUser(User user){
        addDummyUser();
        userDao.insertNewUser(user);
    }

    public User getUser(String id){
       return userDao.getUserById(id);
    }

    public void updateUser(User user){
        userDao.updateUser(user);
    }

    public void deleteUser(User user){
        userDao.deleteUser(user);
    }

    public void updateUserProfileImage(String newImageName, String email){
        userDao.updateUserProfileImage(newImageName, email);

    }
}

