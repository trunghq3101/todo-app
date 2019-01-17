package com.trunghoang.todoapp;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.trunghoang.todoapp.adapters.TodoAdapter;
import com.trunghoang.todoapp.adapters.TodoClickListener;
import com.trunghoang.todoapp.data.TodoUnit;
import com.trunghoang.todoapp.utilities.Constants;
import com.trunghoang.todoapp.utilities.ViewUtils;
import com.trunghoang.todoapp.viewmodels.TodoViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements EditorDialogFragment.Listener {

    public static final int NEW_TODO_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_TODO_ACTIVITY_REQUEST_CODE = 2;
    private TodoViewModel mTodoViewModel;
    private TodoAdapter mTodoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        setActionOpenEditor(fab);

        RecyclerView recyclerView = findViewById(R.id.main_task_recycler_view);
        mTodoAdapter = new TodoAdapter(this);
        recyclerView.setAdapter(mTodoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        attachItemTouchHelper(recyclerView);
        attachItemTouchListener(recyclerView);

        mTodoViewModel = new TodoViewModel(this.getApplication());
        mTodoViewModel.getAllTodos().observe(this, new Observer<List<TodoUnit>>() {
            @Override
            public void onChanged(@Nullable List<TodoUnit> todoUnits) {
                mTodoAdapter.setAllTodos(todoUnits);
            }
        });
    }

    private void setActionOpenEditor(FloatingActionButton fab) {
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
//                startActivityForResult(intent, NEW_TODO_ACTIVITY_REQUEST_CODE);
//            }
//        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditorDialogFragment.newInstance().show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    private void attachItemTouchHelper(final RecyclerView recyclerView) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                TodoUnit todoUnit = ((TodoAdapter) recyclerView.getAdapter())
                        .getTodoAtPosition(position);
                Toast.makeText(MainActivity.this, R.string.deleting_todo_alert,
                        Toast.LENGTH_SHORT).show();
                mTodoViewModel.delete(todoUnit);
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

    private void attachItemTouchListener(final RecyclerView recyclerView) {
        recyclerView.addOnItemTouchListener(new TodoClickListener(this, new TodoClickListener.TodoClickListenerCallback() {
            @Override
            public void onSingleTapUp(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    View underChild = ViewUtils.findChildViewAt(child, e.getX(), e.getY());
                    int position = recyclerView.getChildAdapterPosition(child);
                    TodoUnit todoUnit = mTodoAdapter.getTodoAtPosition(position);

                    if (underChild instanceof AppCompatCheckBox) {
                        todoUnit.setTodoDone(!todoUnit.getTodoDone());
                        mTodoViewModel.update(todoUnit);
                    } else {
                        Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.EXTRA_TODO_UNIT_BUNDLE, todoUnit);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, UPDATE_TODO_ACTIVITY_REQUEST_CODE);
                    }
                }
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_TODO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.getExtras() != null) {
                TodoUnit todoUnit = (TodoUnit) data.getExtras().getSerializable(Constants.EXTRA_TODO_UNIT_BUNDLE);
                boolean isDeleting = data.getExtras().getBoolean(Constants.EXTRA_DELETE_REQUEST);
                if (isDeleting) {
                    mTodoViewModel.delete(todoUnit);
                } else {
                    mTodoViewModel.update(todoUnit);
                }
            } else {
                Toast.makeText(this, R.string.something_wrong, Toast.LENGTH_SHORT)
                        .show();
            }
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

    @Override
    public void onSaveButtonClick(TodoUnit newTodoUnit) {
        if (newTodoUnit != null) {
            mTodoViewModel.insert(newTodoUnit);
        }
    }
}
