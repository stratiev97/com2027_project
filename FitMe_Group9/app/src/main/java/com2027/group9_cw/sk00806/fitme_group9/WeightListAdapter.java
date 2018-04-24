package com2027.group9_cw.sk00806.fitme_group9;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by christopher on 23/04/2018.
 */

public class WeightListAdapter extends ArrayAdapter<WeightDay> {

    ArrayList<WeightDay> weightDays = new ArrayList<>();


    public WeightListAdapter(@NonNull Context context, int resource, ArrayList<WeightDay> weights) {
        super(context, resource, weights);
        this.weightDays = weights;
    }

    @Override
    public int getCount(){
        return super.getCount();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println(weightDays.size());
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.weightitem, null);
        TextView weight = (TextView) v.findViewById(R.id.weightitem_weight);
        TextView date = (TextView) v.findViewById(R.id.weightitem_date);

        weight.setText(Double.toString(weightDays.get(position).getWeight()) + "kg");
        date.setText(weightDays.get(position).getFormattedDate());
        return v;

    }
}
