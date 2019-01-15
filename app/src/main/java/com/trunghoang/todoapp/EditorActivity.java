package com.trunghoang.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditorActivity extends AppCompatActivity {

    public static final String EXTRA_TODO_CONTENT = "com.trunghoang.todoapp.EXTRA_TODO_CONTENT";
    private EditText mContentInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mContentInput = findViewById(R.id.todo_content_input);
    }

    public void saveTodo(View view) {
        if (TextUtils.isEmpty(mContentInput.getText())) {
            Toast.makeText(this,
                    R.string.empty_todo_alert,
                    Toast.LENGTH_LONG).show();
        } else {
            String todoContent = mContentInput.getText().toString();
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TODO_CONTENT, todoContent);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
