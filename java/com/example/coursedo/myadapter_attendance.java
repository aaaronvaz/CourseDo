package com.example.coursedo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class myadapter_attendance extends BaseAdapter {
    private Context context;
    private ArrayList<MyData_Attendance> mylist;

    public myadapter_attendance(Context context, ArrayList<MyData_Attendance> mylist) {
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
        convertView= LayoutInflater.from(context).inflate(R.layout.att,null);

        TextView date=convertView.findViewById(R.id.date);
        TextView time=convertView.findViewById(R.id.att_time);

        date.setText(mylist.get(position).getAttendance_date());
        time.setText(mylist.get(position).getAttendance_time());

        return convertView;

    }
}
