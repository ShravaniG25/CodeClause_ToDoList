package com.example.todolist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {
    //insert qy=uery

    @Insert(onConflict = REPLACE)
    void insert(com.example.todolist.MainData mainData);

    //delete
    @Delete
    void delete(com.example.todolist.MainData mainData);

    //delete all
    @Delete
    void reset(List<com.example.todolist.MainData> mainData);

    //update
    @Query("UPDATE Table_name SET text =:sText WHERE ID = :sID")
    void upate(int sID,String sText);

    //get all data
    @Query("SELECT * FROM table_name")
    List<com.example.todolist.MainData> getAll();
}
