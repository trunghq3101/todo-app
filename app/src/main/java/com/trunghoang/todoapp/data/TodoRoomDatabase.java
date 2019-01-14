package com.trunghoang.todoapp.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {TodoUnit.class}, version = 1, exportSchema = false)
abstract class TodoRoomDatabase extends RoomDatabase {

    abstract TodoDao todoDao();
    private static TodoRoomDatabase instance;

    private static RoomDatabase.Callback sRoomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsync(instance).execute();
        }
    };

    static TodoRoomDatabase getDatabase(Context context) {
        if (instance == null) {
            synchronized (TodoRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                            TodoRoomDatabase.class, "todo_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

    private static class PopulateDBAsync extends AsyncTask<Void, Void, Void> {
        private TodoDao mTodoDao;

        PopulateDBAsync(TodoRoomDatabase db) {
            mTodoDao = db.todoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mTodoDao.insert(new TodoUnit("This is a sample todo task"));
            return null;
        }
    }
}
