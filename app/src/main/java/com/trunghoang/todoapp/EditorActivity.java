package com.trunghoang.todoapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.trunghoang.todoapp.data.TodoUnit;
import com.trunghoang.todoapp.utilities.Constants;

public class EditorActivity extends AppCompatActivity {

    public static final String EXTRA_TODO_CONTENT = "com.trunghoang.todoapp.EXTRA_TODO_CONTENT";
    private EditText mContentInput;
    private TodoUnit mTodoUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mContentInput = findViewById(R.id.todo_content_input);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            mTodoUnit = (TodoUnit) intent.getExtras().getSerializable(Constants.EXTRA_TODO_UNIT_BUNDLE);
            if (mTodoUnit != null) {
                populateTodoView(mTodoUnit);
            }
        }
    }

    private void populateTodoView(TodoUnit todoUnit) {
        mContentInput.setText(todoUnit.getTodoText());
    }

    private TodoUnit updatedTodoUnit(TodoUnit todoUnit) {
        return new TodoUnit.Builder()
                .setTodoId(todoUnit.getId())
                .setTodoText(mContentInput.getText().toString())
                .build();
    }

    public void saveTodo(View view) {
        if (TextUtils.isEmpty(mContentInput.getText())) {
            Toast.makeText(this,
                    R.string.empty_todo_alert,
                    Toast.LENGTH_LONG).show();
        } else {
            String todoContent = mContentInput.getText().toString();
            Intent intent = new Intent();
            if (mTodoUnit == null) {
                intent.putExtra(EXTRA_TODO_CONTENT, todoContent);
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.EXTRA_TODO_UNIT_BUNDLE, updatedTodoUnit(mTodoUnit));
                intent.putExtras(bundle);
            }
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
