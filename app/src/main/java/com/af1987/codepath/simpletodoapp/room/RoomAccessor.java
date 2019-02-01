package com.af1987.codepath.simpletodoapp.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.af1987.codepath.simpletodoapp.ToDoItem;

import java.util.List;

@Dao
public interface RoomAccessor {
    @Insert
    void insertItem(ToDoItem item);


    @Query("SELECT * FROM db_items ORDER BY id desc")
    LiveData<List<ToDoItem>> fetchAllItems();


    @Query("SELECT * FROM db_items WHERE id =:itemId")
    LiveData<ToDoItem> getItem(int itemId);


    @Update
    void updateItem(ToDoItem note);


    @Delete
    void deleteItem(ToDoItem note);


    @Query("DELETE FROM db_items")
    void deleteAll();
}
