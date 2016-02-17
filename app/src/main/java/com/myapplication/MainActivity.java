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

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, listData);

        ListView listView = (ListView)findViewById(R.id.eventlistview);
        listView.setAdapter(arrayAdapter);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //lab -- monday

//        // Retrieve items from server and get count
//        int eventCount = getEventCount();
//        // Build display string
//        String displayText = "Number of events: " + eventCount;
//        // Reference view element and set display text
//        TextView textField = (TextView) findViewById(R.id.textView);
//        textField.setText(displayText);


        displayListView();

    }


}
