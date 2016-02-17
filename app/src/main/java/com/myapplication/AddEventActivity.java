package com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.garagze.event.domain.SaleEvent;
import com.garagze.event.service.SaleEventManager;

public class AddEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        final Button addButton = (Button) findViewById(R.id.addBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final EditText addressView =
                        (EditText) findViewById(R.id.address);
                String address = addressView.getText().toString();
                final EditText descriptionView =
                        (EditText) findViewById(R.id.description);
                String description = descriptionView.getText().toString();
                SaleEvent event = new SaleEvent();
                event.setStreet(address);
                event.setDescription(description);
                event.setRating(3.0F);
                event.setCity("Naperville");
                SaleEventManager.addEvent(AddEventActivity.this, event);
                finish();
            }
        });


        final Button cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });


    }




}
