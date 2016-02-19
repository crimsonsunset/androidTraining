package com.myapplication;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.List;

public class SalesTouchHelper extends ItemTouchHelper.SimpleCallback {
    private SaleEventArrayAdapter mAdapter;
    private List events;
    private static final String TAG = SaleEventArrayAdapter.class.getSimpleName();

    public SalesTouchHelper(SaleEventArrayAdapter mAdapter, List events) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mAdapter = mAdapter;
        this.events = events;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        String logMsg = TAG + "Running MOVEEEE method. : ";
        Log.v(TAG, logMsg);
//        mAdapter.swap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int remInd = viewHolder.getAdapterPosition();
        String logMsg = TAG + "Running SWIPE method. : " + direction + remInd;
        Log.v(TAG, logMsg);

//        events.remove(remInd);
        mAdapter.remove(remInd);
        mAdapter.notifyItemRemoved(remInd);
        mAdapter.notifyItemRangeChanged(remInd, events.size());
//        Toast.makeText(mActivity, logMsg, Toast.LENGTH_LONG).show();

    }




}
