package com.trunghoang.todoapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todo_table WHERE todo_done = 0")
    LiveData<List<TodoUnit>> getAllTodos();

    @Query("SELECT * FROM todo_table WHERE todo_done = 1")
    LiveData<List<TodoUnit>> getDoneTodos();

    @Insert
    void insert(TodoUnit todoUnit);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Delete
    void delete(TodoUnit todoUnit);

    @Update
    void update(TodoUnit todoUnit);
}
