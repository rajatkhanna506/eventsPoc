package com.example.eventspoc.Adapter;

import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.eventspoc.R;
import com.example.eventspoc.models.Event;
import com.example.eventspoc.models.GetAllEvents;
import com.example.eventspoc.models.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class EventsAdapter extends BaseAdapter {

    List<Event> eventsList;
    Context context;
    TextView tvEvent;
    TextView tvEventStartDate;
    ImageView ivEvent;


    public EventsAdapter(Context context, List<Event> eventsList) {
        this.eventsList = eventsList;
        this.context = context;

    }


    @Override
    public int getCount() {
        return eventsList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.events_adapter, null);
        tvEvent = convertView.findViewById(R.id.tvEvent);
        ivEvent = convertView.findViewById(R.id.ivEvent_image);
        tvEventStartDate  = convertView.findViewById(R.id.tvEventDate);
        tvEvent.setText(eventsList.get(position).getName());
        tvEventStartDate.setText(eventsList.get(position).getDates().getStart().getLocalDate());
        Picasso.with(context).load(eventsList.get(position).getImages().get(0).getUrl()).into(ivEvent);
        return convertView;
    }
}
