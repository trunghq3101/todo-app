package com.trunghoang.todoapp.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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

    public void deleteAll() {
        new DeleteAllAsync(mTodoDao).execute();
    }

    public void reset() {
        new ResetAsync(mTodoDao).execute();
    }

    private static class DeleteAllAsync extends AsyncTask<Void, Void, Void> {
        TodoDao mTodoDao;

        DeleteAllAsync(TodoDao mTodoDao) {
            this.mTodoDao = mTodoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mTodoDao.deleteAll();
            return null;
        }
    }

    private static class ResetAsync extends AsyncTask<Void, Void, Void> {
        TodoDao mTodoDao;

        ResetAsync(TodoDao mTodoDao) {
            this.mTodoDao = mTodoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mTodoDao.deleteAll();
            mTodoDao.insert(new TodoUnit("This is a sample todo task"));
            return null;
        }
    }
}
