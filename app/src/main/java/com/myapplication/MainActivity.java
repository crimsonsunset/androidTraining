package com.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.garagze.event.domain.SaleEvent;
import com.garagze.event.service.SaleEventManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static RecyclerView mRecyclerView;
    private static StaggeredGridLayoutManager mStaggeredLayoutManager;
    private static SaleEventArrayAdapter mAdapter;
    private static MainActivity mActivity;
    private static List<SaleEvent> events;


    private void displayRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);

        events = SaleEventManager.getAllEvents(this);
        mAdapter = new SaleEventArrayAdapter(this, events);
        mRecyclerView.setAdapter(mAdapter);
        mActivity = this;

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int remInd = viewHolder.getAdapterPosition();
                String logMsg = TAG + "Running SWIPE method. : " + direction;
                Log.v(TAG, logMsg);

                events.remove(remInd);
                mAdapter.notifyItemRemoved(remInd);
                mAdapter.notifyItemRangeChanged(remInd, events.size());
                Toast.makeText(mActivity, logMsg, Toast.LENGTH_LONG).show();

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

//        public void removeAt(int position) {
//        events.remove(position);
//            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, events.size());
//        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "Running onResume method");
        displayRecyclerView();
    }


    //stuff for menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mi_add_event:
                addEvent();
                return true;
            case R.id.mi_show_map:
                showMap();
                return true;
            case R.id.mi_prefs:
                showPrefs();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showPrefs() {
        Log.v(TAG, "Running showPrefs method.");
    }

    private void showMap() {
        Log.v(TAG, "Running showMap method.");
    }

    private void addEvent() {
        Log.v(TAG, "Running addEvent method.");
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }

}
