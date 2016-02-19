package com.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
//        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1,
//                StaggeredGridLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        events = SaleEventManager.getAllEvents(this);
        mAdapter = new SaleEventArrayAdapter(this, events);
        mRecyclerView.setAdapter(mAdapter);
        mActivity = this;

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback((ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT), (ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)) {


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                String logMsg = TAG + "Running MOVEEEE method. : ";
                Log.v(TAG, logMsg);
                mAdapter.swap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
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


        // Setup ItemTouchHelper
//        ItemTouchHelper.Callback callback = new SalesTouchHelper(mAdapter,events);
//        ItemTouchHelper helper = new ItemTouchHelper(callback);
//        helper.attachToRecyclerView(mRecyclerView);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        String defaultValue="";
        String userName = prefs.getString("PREF_USERNAME", defaultValue).trim();
        String cheese = prefs.getString("PREF_FAV", defaultValue).trim();
        TextView userNameView = (TextView) findViewById(R.id.userName);
        if (userName.equals("")) {
            userNameView.setVisibility(View.INVISIBLE);
        } else {
            userNameView.setVisibility(View.VISIBLE);
            userNameView.setText("Current User: "+userName + " really loves " + cheese);
        }


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
        Intent intent = new Intent(this, Preferences.class);
        startActivity(intent);
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
