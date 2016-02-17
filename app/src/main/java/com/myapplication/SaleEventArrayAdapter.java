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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.event_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.address.setText(event.getStreet() + ", " + event.getCity());
        viewHolder.rating.setRating(event.getRating());
        viewHolder.distance.setText(Double.toString(event.getDistance()));

//        TextView address =
//                (TextView) convertView.findViewById(R.id.item_address);
//        address.setText(event.getStreet() + ", " + event.getCity());
//        RatingBar rating =
//                (RatingBar) convertView.findViewById(R.id.item_rating);
//        rating.setRating(event.getRating());
//        TextView distance =
//                (TextView) convertView.findViewById(R.id.item_distance);
//        distance.setText(Double.toString(event.getDistance()));



//        ViewHolder viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);
        return convertView;
    }

    //used for caching data into views
    static class ViewHolder {
        ViewHolder(View view) {
            address = (TextView) view.findViewById(R.id.item_address);
            rating = (RatingBar) view.findViewById(R.id.item_rating);
            distance = (TextView) view.findViewById(R.id.item_distance);
        }

        public TextView address;
        public RatingBar rating;
        public TextView distance;
    }

}


