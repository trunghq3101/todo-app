package com.trunghoang.todoapp.adapters;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class TodoClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "TodoClickListener";
    private GestureDetectorCompat mGestureDetectorCompat;
    private TodoClickListenerCallback mCallback;

    public TodoClickListener(final Context context, TodoClickListenerCallback callback) {
        mCallback = callback;
        mGestureDetectorCompat = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                mCallback.onSingleTapUp(e);
                return super.onSingleTapUp(e);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onTouchEvent: ");
        super.onTouchEvent(rv, e);
    }

    public interface TodoClickListenerCallback {
        public void onSingleTapUp(MotionEvent e);
    }
}
