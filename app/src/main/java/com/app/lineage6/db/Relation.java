package com.app.lineage6.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "Relations")
public class Relation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "relation_id")
    public int relationId;


    @NonNull
    @ColumnInfo(name = "person1")
    public String person1;

    @NonNull
    @ColumnInfo(name = "person2")
    public String person2;



    @NonNull
    @ColumnInfo(name = "relationb")
    public String relationBetween;


    public Relation(@NonNull String person1,String person2,String relationBetween) {

        this.person1 = person1;
        this.person2 = person2;
        this.relationBetween = relationBetween;

    }

}
