package com.trunghoang.todoapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.trunghoang.todoapp.data.TodoUnit;

public class EditorDialogFragment extends BottomSheetDialogFragment {

    private Listener mListener;
    private EditText mContentInput;

    public static EditorDialogFragment newInstance() {
        return new EditorDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editor_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContentInput = view.findViewById(R.id.todo_content_input);
        Button mSaveButton = view.findViewById(R.id.button_save_todo);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSaveButtonClick(populatedTodoUnit());
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    private TodoUnit populatedTodoUnit() {
        if (TextUtils.isEmpty(mContentInput.getText())) {
            return null;
        } else {
            return new TodoUnit.Builder()
                    .setTodoText(mContentInput.getText().toString())
                    .build();
        }
    }

    public interface Listener {
        void onSaveButtonClick(TodoUnit todoUnit);
    }
}
