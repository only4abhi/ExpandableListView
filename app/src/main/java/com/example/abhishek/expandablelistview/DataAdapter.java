package com.example.abhishek.expandablelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abhishek on 29-03-2017.
 */

public class DataAdapter extends BaseExpandableListAdapter{

    private Context context;
    private ArrayList<Brandinfo> deptlist;

    //Constructor

    public  DataAdapter(Context context,ArrayList<Brandinfo> deptlist){
        this.context  = context;
        this.deptlist = deptlist;
    }

    @Override
    public int getGroupCount() {
        return deptlist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Carinfo> productlist = deptlist.get(groupPosition).getList();
        return productlist.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptlist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Carinfo> productlist = deptlist.get(groupPosition).getList();

        return productlist.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Brandinfo headerinfo = (Brandinfo)getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.branditems,null);
        }

        TextView headings = (TextView)convertView.findViewById(R.id.heading);
        headings.setText(headerinfo.getName().trim());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Carinfo detailinfo = (Carinfo)getChild(groupPosition,childPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.caritems,null);
        }

        TextView sequence = (TextView)convertView.findViewById(R.id.branditems);
        sequence.setText(detailinfo.getSequence().trim()+".");

        TextView childitem = (TextView)convertView.findViewById(R.id.childitems);
        childitem.setText(detailinfo.getName().trim());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
