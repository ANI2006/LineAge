package com.app.lineage6.mvvm;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.lineage6.db.AppDatabase;
import com.app.lineage6.db.DatabaseClient;
import com.app.lineage6.db.RelationDao;
import com.app.lineage6.db.AppRepo;
import com.app.lineage6.db.Person;
import com.app.lineage6.db.UserDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;


    private AppRepo appRepo;
    private MutableLiveData<List<Person>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        appDatabase = DatabaseClient.getInstance(getApplication()).getAppDatabase();
        users = new MutableLiveData<>();


        appRepo= new AppRepo(application) {
            @Override
            public UserDao userDao() {
                return null;
            }
            public RelationDao relationDao  () {
                return null;
            }

        };
    }

    public void insertUser(Person person){
        appRepo.insertUser(person);
    }
    public void addMe(@NonNull String name, String date, String gender,String description) {
        AsyncTask.execute(() -> {
            appDatabase.userDao().insertUser(new Person());
            refreshUserList();
        });
    }


    public void updateUser(Person person){
        appRepo.updateUser(person);
    }

    public void deleteUser(Person person){
        appRepo.deleteUser(person);
    }
    public void refreshUserList() {
        List<Person> updateList = appDatabase.userDao().getAllUserFuture();
        users.postValue(updateList);
    }

    public LiveData<List<Person>> getAllUserFuture() throws ExecutionException,InterruptedException{
        return appRepo.getAllUserLive();
    }
    //public LiveData<List<Person>> getUserNames() {
//        return appRepo.getAllUserNames() ;
//    }


    public LiveData<List<Person>> getAllUserLive(){
        return appRepo.getAllUserLive();
    }
}
