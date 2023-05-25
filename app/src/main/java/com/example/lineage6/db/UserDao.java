package com.example.lineage6.db;

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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(Person person);

    @Update
    void updateUser(Person person);


    @Delete
    void deleteUser(Person person);

    @Query("SELECT * FROM user")
    LiveData<List<Person>> getAllUserLive();

    @Query("SELECT * FROM user")
    List<Person> getAllUserFuture();

    @Query("SELECT * FROM user WHERE uId=:id")
    Person getUser(int id);



//    @Delete
//    List<Integer> deleteListOfUser(List<ProjectModel> projectModelList);
//
//    @Update
//    List<Integer> updateListOfUser(List<ProjectModel> projectModelList);
//
//    @Insert
//    List<Integer> insertListOfUser(List<ProjectModel> projectModelList);




}
