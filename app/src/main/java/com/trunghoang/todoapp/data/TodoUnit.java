package com.trunghoang.todoapp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "todo_table")
public class TodoUnit implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "todo_text")
    private String mTodoText;

    @Ignore
    public TodoUnit(@NonNull String mTodoText) {
        this.mTodoText = mTodoText;
    }

    public TodoUnit(int id, @NonNull String mTodoText) {
        this.id = id;
        this.mTodoText = mTodoText;
    }

    public String getTodoText() {
        return mTodoText;
    }

    public int getId() {
        return id;
    }

    public static class Builder {
        private int id;
        private String mTodoText;

        public Builder() {}

        public Builder setTodoText(@NonNull String todoText) {
            mTodoText = todoText;
            return this;
        }

        public Builder setTodoId(int id) {
            this.id = id;
            return this;
        }

        public TodoUnit build() {
            return new TodoUnit(id, mTodoText);
        }
    }
}
