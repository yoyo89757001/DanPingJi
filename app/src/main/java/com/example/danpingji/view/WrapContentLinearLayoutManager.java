package com.example.danpingji.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;



/**
 * Created by Administrator on 2017/6/29.
 */

public class WrapContentLinearLayoutManager extends LinearLayoutManager {
  //   private RecytviewCash recytviewCash;


    public WrapContentLinearLayoutManager(Context context) {
        super(context);
    }

    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);

    }

    public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);

        } catch (IndexOutOfBoundsException e) {

            Log.d("WrapContentLinearLayout", e.getMessage()+"");
        }
    }
}