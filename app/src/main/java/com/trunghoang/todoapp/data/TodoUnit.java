package com.trunghoang.todoapp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "todo_table")
public class TodoUnit implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "todo_text")
    private String mTodoText;

    @ColumnInfo(name = "todo_deadline")
    private Date mTodoDeadline;

    @Ignore
    public TodoUnit(@NonNull String mTodoText, Date mTodoDeadline) {
        this.mTodoText = mTodoText;
        this.mTodoDeadline = mTodoDeadline;
    }

    public TodoUnit(int id, @NonNull String mTodoText, Date mTodoDeadline) {
        this.id = id;
        this.mTodoText = mTodoText;
        this.mTodoDeadline = mTodoDeadline;
    }

    public String getTodoText() {
        return mTodoText;
    }

    public int getId() {
        return id;
    }

    public Date getTodoDeadline() {
        return mTodoDeadline;
    }

    public static class Builder {
        private int id;
        private String mTodoText;
        private Date mTodoDeadline;

        public Builder() {}

        public Builder setTodoText(@NonNull String todoText) {
            mTodoText = todoText;
            return this;
        }

        public Builder setTodoId(int id) {
            this.id = id;
            return this;
        }

        public Builder setTodoDeadline(Date mTodoDeadline) {
            this.mTodoDeadline = mTodoDeadline;
            return this;
        }

        public TodoUnit build() {
            return new TodoUnit(id, mTodoText, mTodoDeadline);
        }
    }
}
