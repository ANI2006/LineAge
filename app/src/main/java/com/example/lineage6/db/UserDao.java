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
    void insertUser(ProjectModel projectModel);

    @Update
    void updateUser(ProjectModel projectModel);


    @Delete
    void deleteUser(ProjectModel projectModel);

    @Query("SELECT * FROM user")
    LiveData<List<ProjectModel>> getAllUserLive();

    @Query("SELECT * FROM user")
    List<ProjectModel> getAllUserFuture();

    @Query("SELECT * FROM user WHERE uId=:id")
    ProjectModel getUser(int id);



//    @Delete
//    List<Integer> deleteListOfUser(List<ProjectModel> projectModelList);
//
//    @Update
//    List<Integer> updateListOfUser(List<ProjectModel> projectModelList);
//
//    @Insert
//    List<Integer> insertListOfUser(List<ProjectModel> projectModelList);




}
