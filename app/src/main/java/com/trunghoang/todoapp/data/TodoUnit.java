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

    @ColumnInfo(name = "todo_done")
    private boolean mTodoDone;

    @Ignore
    public TodoUnit(@NonNull String mTodoText, Date mTodoDeadline, boolean mTodoDone) {
        this.mTodoText = mTodoText;
        this.mTodoDeadline = mTodoDeadline;
        this.mTodoDone = mTodoDone;
    }

    public TodoUnit(int id, @NonNull String mTodoText, Date mTodoDeadline, boolean mTodoDone) {
        this.id = id;
        this.mTodoText = mTodoText;
        this.mTodoDeadline = mTodoDeadline;
        this.mTodoDone = mTodoDone;
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

    public boolean getTodoDone() { return mTodoDone; }

    public void setTodoDone(boolean done) { mTodoDone = done; }

    public static class Builder {
        private int id;
        private String mTodoText;
        private Date mTodoDeadline;
        private boolean mTodoDone;

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

        public Builder setTodoDone(boolean mTodoDone) {
            this.mTodoDone = mTodoDone;
            return this;
        }

        public TodoUnit build() {
            return new TodoUnit(id, mTodoText, mTodoDeadline, mTodoDone);
        }
    }
}
