package com.trunghoang.todoapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trunghoang.todoapp.R;
import com.trunghoang.todoapp.data.TodoUnit;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    List<TodoUnit> mAllTodos;
    final LayoutInflater mINFLATER;

    public TodoAdapter(Context context) {
        this.mINFLATER = LayoutInflater.from(context);
    }

    public void setAllTodos(List<TodoUnit> allTodos) {
        mAllTodos = allTodos;
        notifyDataSetChanged();
    }

    public TodoUnit getTodoAtPosition(int position) {
        return mAllTodos.get(position);
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mINFLATER.inflate(R.layout.recycler_view_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        if (mAllTodos != null) {
            TodoUnit todoUnit = mAllTodos.get(position);
            holder.todoTextView.setText(todoUnit.getTodoText());
            holder.todoCheckBox.setChecked(todoUnit.getTodoDone());
        }
    }

    @Override
    public int getItemCount() {
        if (mAllTodos != null) {
            return mAllTodos.size();
        } else return 0;
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        private TextView todoTextView;
        private AppCompatCheckBox todoCheckBox;

        TodoViewHolder(View itemView) {
            super(itemView);
            todoTextView = itemView.findViewById(R.id.item_text);
            todoCheckBox = itemView.findViewById(R.id.item_check);
        }
    }
}
