package com.example.lineage6.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RelationDao {
    @Query("SELECT * FROM Relations")
    List<Relation> getAllRelations();

    @Insert
    void insertRelation(Relation relation);

    @Update
    void updateRelation(Relation relation);

    @Delete
    void deleteRelation(Relation relation);

    @Query("SELECT * FROM Relations WHERE relation_id = :relationId")
    Relation getRelationById(int relationId);
}
