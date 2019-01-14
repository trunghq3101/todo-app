package com.trunghoang.todoapp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "todo_table")
public class TodoUnit {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "todo_text")
    private String mTodoText;

    public TodoUnit(@NonNull String mTodoText) {
        this.mTodoText = mTodoText;
    }

    @Ignore
    public TodoUnit(int id, @NonNull String mTodoText) {
        this.mTodoText = mTodoText;
    }

    public String getTodoText() {
        return mTodoText;
    }

    public int getId() {
        return id;
    }
}
