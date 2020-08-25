package com.example.accelermeterrecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ThreeColumn_ListAdapter extends ArrayAdapter<Reading> {
    private LayoutInflater mInflater;
    private ArrayList<Reading> readings;
    private int mViewResourceId;

    public ThreeColumn_ListAdapter (Context context, int textViewResourceId, ArrayList<Reading> readings){
        super(context,textViewResourceId,readings);
        this.readings = readings;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents) {
        convertView = mInflater.inflate(mViewResourceId,null);
        Reading reading = readings.get(position);
        if (reading != null){
            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView x_acc = (TextView) convertView.findViewById(R.id.x_acc);
            TextView y_acc = (TextView) convertView.findViewById(R.id.y_acc);
            TextView z_acc = (TextView) convertView.findViewById(R.id.z_acc);

            if (time != null){
                time.setText((reading.getTime()));
            }
            if (x_acc != null){
                x_acc.setText((reading.getX_acc()));
            }
            if (y_acc != null){
                y_acc.setText((reading.getY_acc()));
            }
            if (z_acc != null){
                z_acc.setText((reading.getZ_acc()));
            }
        }
        return convertView;
    }
}

