package com.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import com.garagze.event.domain.SaleEvent;

import java.util.List;

public class SaleEventArrayAdapter extends ArrayAdapter<SaleEvent> {
    public SaleEventArrayAdapter
            (Context context, int resource, List<SaleEvent> events) {
        super(context, resource, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SaleEvent event = getItem(position);
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.event_list_item, null);
        }
        TextView address =
                (TextView) convertView.findViewById(R.id.item_address);
        address.setText(event.getStreet() + ", " + event.getCity());
        RatingBar rating =
                (RatingBar) convertView.findViewById(R.id.item_rating);
        rating.setRating(event.getRating());
        TextView distance =
                (TextView) convertView.findViewById(R.id.item_distance);
        distance.setText(Double.toString(event.getDistance()));
        return convertView;
    }

}
