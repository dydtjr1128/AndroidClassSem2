package org.androidtown.myapplication;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by CYSN on 2017-09-26.
 */

public class MyRecyclerViewDecoration extends RecyclerView.ItemDecoration {
    private  int marginSize;
    public MyRecyclerViewDecoration(int size){
        this.marginSize = size;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = marginSize;
        outRect.top = marginSize;
        outRect.left = marginSize;
        outRect.right = marginSize;
    }
}
