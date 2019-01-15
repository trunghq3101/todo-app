package com.trunghoang.todoapp;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.trunghoang.todoapp.adapters.TodoAdapter;
import com.trunghoang.todoapp.data.TodoUnit;
import com.trunghoang.todoapp.viewmodels.TodoViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_TODO_ACTIVITY_REQUEST_CODE = 1;
    private TodoViewModel mTodoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivityForResult(intent, NEW_TODO_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.main_task_recycler_view);
        final TodoAdapter todoAdapter = new TodoAdapter(this);
        recyclerView.setAdapter(todoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTodoViewModel = new TodoViewModel(this.getApplication());
        mTodoViewModel.getAllTodos().observe(this, new Observer<List<TodoUnit>>() {
            @Override
            public void onChanged(@Nullable List<TodoUnit> todoUnits) {
                todoAdapter.setAllTodos(todoUnits);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TODO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String todoContent = data.getStringExtra(EditorActivity.EXTRA_TODO_CONTENT);
            mTodoViewModel.insert(new TodoUnit.Builder()
                    .setTodoText(todoContent)
                    .build());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all) {
            mTodoViewModel.deleteAll();
            return true;
        }

        if (id == R.id.action_reset) {
            mTodoViewModel.reset();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
