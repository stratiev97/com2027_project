package com2027.group9_cw.sk00806.fitme_group9;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<ProgressPicture> pictures = new ArrayList<>();
    Activity activity;

    public RecyclerAdapter(ArrayList<ProgressPicture> arrayList, Context context){
        this.pictures = arrayList;
        activity = (Activity) context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String dateText = getFormattedDate(pictures.get(position).getImageTitle());

        holder.datetext.setText(dateText);
        String path = pictures.get(position).getUrl();
        Glide.with(activity).load(path).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView datetext;

        public MyViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView)itemView.findViewById(R.id.thumbnail);
            datetext = (TextView)itemView.findViewById(R.id.picdate);


        }
    }

    public String getFormattedDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            Date datetxt = format.parse(date);
            SimpleDateFormat format2 = new SimpleDateFormat("MMMM dd");
            date = format2.format(datetxt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }
}
