package com.trunghoang.todoapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.trunghoang.todoapp.data.TodoUnit;
import com.trunghoang.todoapp.utilities.Constants;

public class EditorActivity extends AppCompatActivity {

    private EditText mContentInput;
    private TodoUnit mTodoUnit;
    private boolean mIsDeleting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        if (getSupportActionBar() == null) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mContentInput = findViewById(R.id.todo_content_input);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            mTodoUnit = (TodoUnit) intent.getExtras().getSerializable(Constants.EXTRA_TODO_UNIT_BUNDLE);
            if (mTodoUnit != null) {
                populateTodoView(mTodoUnit);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    private void populateTodoView(TodoUnit todoUnit) {
        mContentInput.setText(todoUnit.getTodoText());
    }

    private TodoUnit populatedTodoUnit(@NonNull TodoUnit todoUnit) {
        return new TodoUnit.Builder()
                .setTodoId(todoUnit.getId())
                .setTodoText(mContentInput.getText().toString())
                .build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.delete) {
            mIsDeleting = true;
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        saveTodo();
        super.finish();
    }

    public void saveTodo() {
        Intent intent = new Intent();
        if (mTodoUnit != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.EXTRA_TODO_UNIT_BUNDLE, populatedTodoUnit(mTodoUnit));
            intent.putExtras(bundle);
        }
        if (mIsDeleting) intent.putExtra(Constants.EXTRA_DELETE_REQUEST, true);
        setResult(RESULT_OK, intent);
    }
}
