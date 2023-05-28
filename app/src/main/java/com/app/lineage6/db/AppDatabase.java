package com.app.lineage6.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Person.class,Relation.class},exportSchema = false,version = 10)
public abstract class AppDatabase extends RoomDatabase {



    public static  final String DATABASE_NAME="user_in_database8.db";
    public static AppDatabase instance;
    private static  final  Object LOCK=new Object();
    public   abstract UserDao userDao();
    public abstract RelationDao relationDao();


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
