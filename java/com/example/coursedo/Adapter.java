package com.example.coursedo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private Context context;
    ArrayList<MyData> mylist;

    public Adapter(Context context, ArrayList<MyData> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @Override
    public int getCount() {
        return mylist.size();
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

        convertView= LayoutInflater.from(context).inflate(R.layout.template_item,null);

        ImageView imageView=convertView.findViewById(R.id.course_icon);
        TextView coursefield=convertView.findViewById(R.id.course_title);
        TextView subfield=convertView.findViewById(R.id.course_subtitle);

        imageView.setImageResource(R.drawable.classroom);
        coursefield.setText(mylist.get(position).getClassroom_name());
        subfield.setText(mylist.get(position).getSub_name());
        return convertView;
    }
}

