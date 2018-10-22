package com.example.hp.challengecup.adapter.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.hp.challengecup.adapter.recyclerview.HomeHorizontalItem;

import java.util.List;

public class HomeListAdapter extends ArrayAdapter<HomeHorizontalItem> {
    private int resourceId;

    public HomeListAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        HomeHorizontalItem item = getItem(position);
//        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
//        ((ImageView)view.findViewById(R.id.home_listItem_iv)).setImageResource(item.imgId);
//        return view;
        return null;
    }
}
