package com.example.coursedo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class sc_adapter extends BaseAdapter {
    private String txt;
    private Context context;

    public sc_adapter(String txt, Context context) {
        this.txt = txt;
        this.context = context;
    }
    @Override
    public int getCount() {
        return 1;
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
        convertView= LayoutInflater.from(context).inflate(R.layout.course_screen_emptymessage,null);

        TextView title=convertView.findViewById(R.id.cs_message_title);
        title.setText(txt);
        return convertView;
    }
}
