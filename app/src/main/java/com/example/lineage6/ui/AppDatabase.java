package com.example.lineage6.ui;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;

import com.example.lineage6.ui.user.ProjectModel;
import com.example.lineage6.ui.user.UserDao;

import java.util.Date;

@Database(entities = {ProjectModel.class},exportSchema = false,version = 8)
public abstract class AppDatabase extends RoomDatabase {



    public static  final String DATABASE_NAME="user_in_database5.db";
    public static AppDatabase instance;
    private static  final  Object LOCK=new Object();
    public   abstract UserDao userDao();

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance==null){
                    instance= Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class,DATABASE_NAME)
                            .build();
                }
            }
        }
        return instance;
    }


}
