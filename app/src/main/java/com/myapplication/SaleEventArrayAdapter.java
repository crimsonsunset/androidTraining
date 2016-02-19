package com.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.garagze.event.domain.SaleEvent;

import java.util.Collections;
import java.util.List;

public class SaleEventArrayAdapter extends RecyclerView.Adapter<SaleEventArrayAdapter.ViewHolder>  {
    private static final String TAG = SaleEventArrayAdapter.class.getSimpleName();
    Context mContext;
    List<SaleEvent> events;

    public SaleEventArrayAdapter(Context context, List<SaleEvent> events) {
        this.mContext = context;
        this.events = events;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView address;
        public RatingBar rating;
        public TextView distance;

        public ViewHolder(View itemView) {
            super(itemView);
            address = (TextView) itemView.findViewById(R.id.item_address);
            rating = (RatingBar) itemView.findViewById(R.id.item_rating);
            distance = (TextView) itemView.findViewById(R.id.item_distance);
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void remove(int remInd) {
        events.remove(remInd);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .event_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SaleEvent event = events.get(position);
        holder.address.setText(event.getStreet() + ", " + event.getCity());
        holder.rating.setRating(event.getRating());
        holder.distance.setText(Double.toString(event.getDistance()));
    }

    public void swap(int firstPosition, int secondPosition){
        String logMsg = "BEFORE SWAPED method. : " + events;
        Log.v(TAG, logMsg);
        Collections.swap(events, firstPosition, secondPosition);
        logMsg = "After SWAPED method. : " + events;
        Log.v(TAG, logMsg);
        notifyItemMoved(firstPosition, secondPosition);
    }

}


//public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
//
//    private final ItemTouchHelperAdapter mAdapter;
//
//    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
//        mAdapter = adapter;
//    }
//
//    @Override
//    public boolean isLongPressDragEnabled() {
//        return true;
//    }
//
//    @Override
//    public boolean isItemViewSwipeEnabled() {
//        return false;
//    }
//
//    @Override
//    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//        return makeMovementFlags(dragFlags, swipeFlags);
//    }
//
//    @Override
//    public boolean onMove(RecyclerView recyclerView,
//                          RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
//        mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
//        return true;
//    }
//
//    @Override
//    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
//        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
//    }
//
//    @Override
//    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
//            ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
//            itemViewHolder.onItemSelected();
//        }
//
//        super.onSelectedChanged(viewHolder, actionState);
//    }
//
//    @Override
//    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        super.clearView(recyclerView, viewHolder);
//
//        ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
//        itemViewHolder.onItemClear();
//    }
//}