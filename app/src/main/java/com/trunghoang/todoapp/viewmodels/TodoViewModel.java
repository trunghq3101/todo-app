package com.trunghoang.todoapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.trunghoang.todoapp.data.TodoRepository;
import com.trunghoang.todoapp.data.TodoUnit;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepository mTodoRepository;
    private LiveData<List<TodoUnit>> mAllTodos;
    private LiveData<List<TodoUnit>> mDoneTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        mTodoRepository = new TodoRepository(application);
        mAllTodos = mTodoRepository.getAllTodos();
        mDoneTodos = mTodoRepository.getDoneTodos();
    }

    public LiveData<List<TodoUnit>> getAllTodos() {
        return mAllTodos;
    }

    public LiveData<List<TodoUnit>> getDoneTodos() {
        return mDoneTodos;
    }

    public void insert(TodoUnit todoUnit) {
        mTodoRepository.insert(todoUnit);
    }

    public void deleteAll() {
        mTodoRepository.deleteAll();
    }

    public void reset() {
        mTodoRepository.reset();
    }

    public void delete(TodoUnit... todoUnits) {
        mTodoRepository.delete(todoUnits);
    }

    public void update(TodoUnit todoUnit) {
        mTodoRepository.update(todoUnit);
    }
}
