package org.androidtown.myapplication;

import java.util.ArrayList;

/**
 * Created by CYSN on 2017-09-26.
 */

public class Contact {
    private String name;
    public Contact(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public static ArrayList<Contact> createArrayList(int size){
        ArrayList<Contact> arrayList = new ArrayList<Contact>();
        int pos = 0;
        for(int i=0; i<size; i++){
            arrayList.add(new Contact("PERSON" + i));
        }
        return arrayList;
    }
}
