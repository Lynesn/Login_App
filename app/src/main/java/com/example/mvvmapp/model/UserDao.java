package com.example.mvvmapp.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    LiveData<List<User>> getUsers();

    @Query("SELECT * FROM users WHERE email == :email")
    User getUserById(String email);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("UPDATE users SET profileImageName = :newImage WHERE email = :email")
    void updateUserProfileImage(String newImage, String email);
}
