package com.example.lineage6.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.lineage6.db.AppRepo;
import com.example.lineage6.db.Person;
import com.example.lineage6.db.UserDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserViewModel extends AndroidViewModel {



    private AppRepo appRepo;

    public UserViewModel(@NonNull Application application) {
        super(application);

        appRepo= new AppRepo(application) {
            @Override
            public UserDao userDao() {
                return null;
            }
        };
    }

    public void insertUser(Person person){
        appRepo.insertUser(person);
    }

    public void updateUser(Person person){
        appRepo.updateUser(person);
    }

    public void deleteUser(Person person){
        appRepo.deleteUser(person);
    }

    public LiveData<List<Person>> getAllUserFuture() throws ExecutionException,InterruptedException{
        return appRepo.getAllUserLive();
    }

    public LiveData<List<Person>> getAllUserLive(){
        return appRepo.getAllUserLive();
    }
}
