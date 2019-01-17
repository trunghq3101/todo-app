package com.trunghoang.todoapp.utilities;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {
    public static View findChildViewAt(View parent, float x, float y) {
        if (parent instanceof ViewGroup) {
            ViewGroup v = (ViewGroup) parent;
            final int count = v.getChildCount();
            for (int i = count - 1; i >= 0; i--) {
                final View child = v.getChildAt(i);
                final float translationX = child.getTranslationX();
                final float translationY = child.getTranslationY();
                if (x >= child.getLeft() + translationX
                        && x <= child.getRight() + translationX
                        && y >= child.getTop() + translationY
                        && y <= child.getBottom() + translationY) {
                    return child;
                }
            }
        }
        return null;
    }
}
