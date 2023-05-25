package com.example.lineage6.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class AppRepo {



    private AppDatabase appDatabase;
    public   abstract UserDao userDao();
    private Executor executor= Executors.newSingleThreadExecutor();

    public AppRepo(Context context){
        appDatabase=AppDatabase.getInstance(context);

    }


    public void  insertUser(Person person){

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.userDao().insertUser(person);

            }
        });
    }

    public void updateUser(Person person){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.userDao().updateUser(person);
            }
        });
    }

    public void deleteUser(Person person){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.userDao().deleteUser(person);
            }
        });
    }

    public List<Person> getAllUserFuture() throws ExecutionException,InterruptedException {

        Callable<List<Person>> callable=new Callable<List<Person>>() {
            @Override
            public List<Person> call() throws Exception {
                return appDatabase.userDao().getAllUserFuture();
            }
        };

        Future<List<Person>> future=Executors.newSingleThreadExecutor().submit(callable);
        return future.get();


    }

    public LiveData<List<Person>> getAllUserLive() {

        return appDatabase.userDao().getAllUserLive();



    }

}