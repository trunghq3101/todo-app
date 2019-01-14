package com.trunghoang.todoapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todo_table")
    LiveData<List<TodoUnit>> getAllTodos();

    @Insert
    void insert(TodoUnit todoUnit);
}
