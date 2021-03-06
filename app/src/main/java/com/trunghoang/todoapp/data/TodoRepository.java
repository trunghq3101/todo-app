package com.trunghoang.todoapp.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TodoRepository {
    private TodoDao mTodoDao;
    private LiveData<List<TodoUnit>> mAllTodos;
    private LiveData<List<TodoUnit>> mDoneTodos;

    public TodoRepository(Application application) {
        TodoRoomDatabase db = TodoRoomDatabase.getDatabase(application);
        mTodoDao = db.todoDao();
        mAllTodos = mTodoDao.getAllTodos();
        mDoneTodos = mTodoDao.getDoneTodos();
    }

    public LiveData<List<TodoUnit>> getAllTodos() {
        return mAllTodos;
    }

    public LiveData<List<TodoUnit>> getDoneTodos() {
        return mDoneTodos;
    }

    public void deleteAll() {
        new DeleteAllAsync(mTodoDao).execute();
    }

    public void reset() {
        new ResetAsync(mTodoDao).execute();
    }

    public void insert(TodoUnit todoUnit) {
        new InsertAsync(mTodoDao).execute(todoUnit);
    }

    public void delete(TodoUnit... todoUnits) {
        new DeleteAsync(mTodoDao).execute(todoUnits);
    }

    public void update(TodoUnit todoUnit) {
        new UpdateAsync(mTodoDao).execute(todoUnit);
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
            mTodoDao.insert(new TodoUnit.Builder()
                    .setTodoText("This is a sample todo task")
                    .build());
            return null;
        }
    }

    private static class InsertAsync extends AsyncTask<TodoUnit, Void, Void> {
        TodoDao mTodoDao;

        InsertAsync(TodoDao mTodoDao) {
            this.mTodoDao = mTodoDao;
        }

        @Override
        protected Void doInBackground(TodoUnit... todoUnits) {
            mTodoDao.insert(todoUnits[0]);
            return null;
        }
    }

    private static class DeleteAsync extends AsyncTask<TodoUnit, Void, Void> {
        TodoDao mTodoDao;

        DeleteAsync(TodoDao mTodoDao) {
            this.mTodoDao = mTodoDao;
        }

        @Override
        protected Void doInBackground(TodoUnit... todoUnits) {
            for (int i = 0; i < todoUnits.length; i++) {
                mTodoDao.delete(todoUnits[i]);
            }
            return null;
        }
    }

    private static class UpdateAsync extends AsyncTask<TodoUnit, Void, Void> {
        TodoDao mTodoDao;

        UpdateAsync(TodoDao mTodoDao) {
            this.mTodoDao = mTodoDao;
        }

        @Override
        protected Void doInBackground(TodoUnit... todoUnits) {
            mTodoDao.update(todoUnits[0]);
            return null;
        }
    }
}
