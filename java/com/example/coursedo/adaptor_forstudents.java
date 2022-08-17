package com.example.coursedo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptor_forstudents extends BaseAdapter {
    private Context context;
    private ArrayList<MyData_Student> mylist;

    public adaptor_forstudents(Context context, ArrayList<MyData_Student> mylist) {
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
        convertView= LayoutInflater.from(context).inflate(R.layout.grade_item,null);

        TextView id=convertView.findViewById(R.id.grade_temp_id);
        TextView name=convertView.findViewById(R.id.grade_temp_title);

        id.setText(mylist.get(position).getId());
        name.setText(mylist.get(position).getFullname());

        return convertView;
    }
}
