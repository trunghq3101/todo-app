package com.trunghoang.todoapp.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {
    private TodoDao mTodoDao;
    private LiveData<List<TodoUnit>> mAllTodos;

    public TodoRepository(Application application) {
        TodoRoomDatabase db = TodoRoomDatabase.getDatabase(application);
        mTodoDao = db.todoDao();
        mAllTodos = mTodoDao.getAllTodos();
    }

    public LiveData<List<TodoUnit>> getAllTodos() {
        return mAllTodos;
    }
}
