package com.trunghoang.todoapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.trunghoang.todoapp.data.TodoUnit;
import com.trunghoang.todoapp.utilities.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditorActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText mContentInput;
    private TextView mDeadlineInputView;
    private TodoUnit mTodoUnit;
    private boolean mIsDeleting = false;
    private long mDeadlineInMillis = 0L;

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
        mDeadlineInputView = findViewById(R.id.todo_deadline_input);
        mDeadlineInputView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment.newInstance(mDeadlineInMillis)
                        .show(getSupportFragmentManager(), "datePicker");
            }
        });

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

    //TODO: keep date on config changes
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        mDeadlineInMillis = c.getTimeInMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy", Locale.US);
        String dateString = formatter.format(c.getTime());
        mDeadlineInputView.setText(dateString);
    }
}
