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
import com.trunghoang.todoapp.utilities.Converters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditorActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText mContentInput;
    private TextView mDeadlineInputView;
    private TodoUnit mTodoUnit;
    private boolean mIsDeleting = false;
    private Long mDeadlineInMillis;
    private String mDeadlineString;

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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mDeadlineInMillis != null) {
            outState.putLong(Constants.EXTRA_TIMEINMILLIS, mDeadlineInMillis);
        }
        if (mDeadlineString != null) {
            outState.putString(Constants.EXTRA_DEADLINE_STRING, mDeadlineString);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(Constants.EXTRA_TIMEINMILLIS)) {
            mDeadlineInMillis = savedInstanceState.getLong(Constants.EXTRA_TIMEINMILLIS);
        }
        if (savedInstanceState.containsKey(Constants.EXTRA_DEADLINE_STRING)) {
            mDeadlineString = savedInstanceState.getString(Constants.EXTRA_DEADLINE_STRING);
            mDeadlineInputView.setText(mDeadlineString);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    private void populateTodoView(TodoUnit todoUnit) {
        mContentInput.setText(todoUnit.getTodoText());
        if (todoUnit.getTodoDeadline() != null) {
            mDeadlineInputView.setText(stringFromDate(todoUnit.getTodoDeadline()));
            mDeadlineInMillis = Converters.dateToTimestamp(todoUnit.getTodoDeadline());
        }
    }

    private TodoUnit populatedTodoUnit(@NonNull TodoUnit todoUnit) {

        TodoUnit.Builder builder = new TodoUnit.Builder();
        builder.setTodoId(todoUnit.getId());
        if (mDeadlineInMillis != null) {
            builder.setTodoDeadline(Converters.fromTimestamp(mDeadlineInMillis));
        }
        builder.setTodoText(mContentInput.getText().toString());
        builder.setTodoDone(todoUnit.getTodoDone());
        return builder.build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            mIsDeleting = true;
            saveTodo(new Intent());
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void supportNavigateUpTo(@NonNull Intent upIntent) {
        saveTodo(upIntent);
        super.supportNavigateUpTo(upIntent);
    }

    @Override
    public void onBackPressed() {
        saveTodo(new Intent());
        super.onBackPressed();
    }

    public void saveTodo(Intent intent) {
        if (mTodoUnit != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.EXTRA_TODO_UNIT_BUNDLE, populatedTodoUnit(mTodoUnit));
            intent.putExtras(bundle);
        }
        if (mIsDeleting) intent.putExtra(Constants.EXTRA_DELETE_REQUEST, true);
        setResult(RESULT_OK, intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        mDeadlineInMillis = c.getTimeInMillis();
        mDeadlineString = stringFromDate(c.getTime());
        mDeadlineInputView.setText(mDeadlineString);
    }

    private String stringFromDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy", Locale.US);
        return formatter.format(date);
    }
}
