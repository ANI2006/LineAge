package com.app.lineage.mvvm;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.lineage.db.AppDatabase;
import com.app.lineage.db.DatabaseClient;
import com.app.lineage.db.Relation;

import java.util.List;

public class RelationViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private MutableLiveData<List<Relation>> relations;

    public RelationViewModel(@NonNull Application application) {
        super(application);
        relations = new MutableLiveData<>();
        appDatabase = DatabaseClient.getInstance(getApplication()).getAppDatabase();
        AsyncTask.execute(this::refreshRelationList);
    }

    public LiveData<List<Relation>> getRelations() {
        return relations;
    }

    public void addRelation(@NonNull String person1,String person2,String relationBetween) {
        AsyncTask.execute(() -> {
            appDatabase.relationDao().insertRelation(new Relation(person1, person2,relationBetween));
            refreshRelationList();
        });
    }

    public void deleteRelation(Relation relation) {
        AsyncTask.execute(() -> {
            appDatabase.relationDao().deleteRelation(relation);
            refreshRelationList();
        });
    }

    public void refreshRelationList() {
        List<Relation> updateList = appDatabase.relationDao().getAllRelations();
        relations.postValue(updateList);
    }
//    public void readRequest() {
//        AsyncTask.execute(() -> {
//            refreshRelationList();
//        });
//    }

    public void updateRelation(Relation relation) {
        AsyncTask.execute(() -> {
            appDatabase.relationDao().updateRelation(relation);
            refreshRelationList();
        });
    }
}
