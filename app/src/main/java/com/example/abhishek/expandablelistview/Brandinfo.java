package com.example.abhishek.expandablelistview;

import java.util.ArrayList;

/**
 * Created by Abhishek on 29-03-2017.
 */

public class Brandinfo {

    private String Name;
    private ArrayList<Carinfo> list = new ArrayList<Carinfo>();

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<Carinfo> getList() {
        return list;
    }

    public void setList(ArrayList<Carinfo> list) {
        this.list = list;
    }


}
