package com.example.coursedo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptor_assessment extends BaseAdapter {
    private Context context;
    private ArrayList<mydata_assessment> mylist;

    public adaptor_assessment(Context context, ArrayList<mydata_assessment> mylist) {
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
        TextView txt;
        convertView= LayoutInflater.from(context).inflate(R.layout.assess_lay,null);

        txt=convertView.findViewById(R.id.textView);
        txt.setText(mylist.get(position).getTitle());
        return convertView;
    }
}
