package com.example.coursedo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class myadaptor_markattendance  extends BaseAdapter {
    private Context context;
    ArrayList<MyData_Student> mylist;

    public myadaptor_markattendance(Context context, ArrayList<MyData_Student> mylist) {
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
        convertView= LayoutInflater.from(context).inflate(R.layout.mark_attend,null);

        TextView id=convertView.findViewById(R.id.mark_attend_id);
        TextView name=convertView.findViewById(R.id.mark_attend_name);

        id.setText(mylist.get(position).getId());
        name.setText(mylist.get(position).getFullname());

        convertView.setBackgroundColor(Color.RED);

        return convertView;
    }
}

