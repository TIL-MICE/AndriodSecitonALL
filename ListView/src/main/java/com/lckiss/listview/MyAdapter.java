package com.lckiss.listview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by root on 17-4-18.
 */

public class MyAdapter extends BaseAdapter {
    private List<Music> sourseDates;
    private Context context;
    private View rowLayoutView;

    public MyAdapter(Context context, List<Music> sourseDates) {
        this.sourseDates = sourseDates;
        this.context = context;

    }

    @Override
    public int getCount() {
        return sourseDates.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        rowLayoutView = layoutInflater.inflate(R.layout.item, null);

        ImageView img=(ImageView)rowLayoutView.findViewById(R.id.musicImg);
        img.setImageResource(sourseDates.get(position).getMusicImg());
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"我是图片",Toast.LENGTH_SHORT).show();
            }
        });

        TextView name = (TextView) rowLayoutView.findViewById(R.id.musicName);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"我是文字",Toast.LENGTH_SHORT).show();
            }
        });

        View item = (View) rowLayoutView.findViewById(R.id.item);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"我是这个盒子",Toast.LENGTH_SHORT).show();
            }
        });

        name.setText(sourseDates.get(position).getMusicName());

        Log.i("info", "getView: "+position);
        return rowLayoutView;
    }
}
