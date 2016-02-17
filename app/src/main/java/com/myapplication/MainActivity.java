package com.myapplication;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


}
