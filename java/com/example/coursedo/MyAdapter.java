package com.example.coursedo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    final Context context;
    final ArrayList<MyData> mylist;

    public MyAdapter(Context context, ArrayList<MyData> mylist) {
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
        TextView title,subtitle;
        ImageView icon;

        convertView= LayoutInflater.from(context).inflate(R.layout.template_item,null);

        title=convertView.findViewById(R.id.course_title);
        subtitle=convertView.findViewById(R.id.course_subtitle);
        icon=convertView.findViewById(R.id.course_icon);

        title.setText(mylist.get(position).getClassroom_name());
        subtitle.setText(mylist.get(position).getSub_name());
        icon.setImageResource(R.drawable.classroom);
        return convertView;
    }
}
