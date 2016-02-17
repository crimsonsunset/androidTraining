package com.myapplication;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.garagze.event.domain.SaleEvent;
import com.garagze.event.service.SaleEventManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //helper functions
    private int getEventCount() {
        return 100;
    }
    private void displayListView() {
        List<SaleEvent> events = SaleEventManager.getAllEvents(this);

        List<String> listData = new ArrayList<String>();
        for (SaleEvent event : events) {
            listData.add(event.getStreet());
        }

//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, listData);

        final ArrayAdapter<SaleEvent> arrayAdapter =
                new SaleEventArrayAdapter(this, R.layout.event_list_item, events);

        ListView listView = (ListView)findViewById(R.id.eventlistview);
        listView.setAdapter(arrayAdapter);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayListView();

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

    private static final String TAG = MainActivity.class.getSimpleName();
    private void showPrefs(){
        Log.v(TAG, "Running showPrefs method.");
    }
    private void showMap() {
        Log.v(TAG, "Running showMap method.");
    }
    private void addEvent() {
        Log.v(TAG, "Running addEvent method.");
    }

}
