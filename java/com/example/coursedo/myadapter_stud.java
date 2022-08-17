package com.example.coursedo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class myadapter_stud extends BaseAdapter {
    private Context context;
    private ArrayList<MyData_Student> mylist;

    public myadapter_stud(Context context, ArrayList<MyData_Student> mylist) {
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
        TextView id,name;
        ImageView icon;

        convertView= LayoutInflater.from(context).inflate(R.layout.template_item,null);

        id=convertView.findViewById(R.id.course_title);
        name=convertView.findViewById(R.id.course_subtitle);
        icon=convertView.findViewById(R.id.course_icon);

        id.setText(mylist.get(position).getId());
        name.setText(mylist.get(position).getFullname());
        icon.setImageResource(R.drawable.student_icon);
        return convertView;
    }
}
