package org.androidtown.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CYSN on 2017-10-12.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    private ArrayList<MyRecyclerItem> myRecyclerItems;
    private Context context;
    public MyRecyclerAdapter(Context context) {
        this.context = context;
        this.myRecyclerItems = new ArrayList<>();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView latitudeTextView;
        TextView longitudeTextView;
        TextView addressTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            latitudeTextView = (TextView) itemView.findViewById(R.id.latitudeTextView);
            longitudeTextView = (TextView) itemView.findViewById(R.id.longitudeTextView);
            addressTextView = (TextView) itemView.findViewById(R.id.addressTextView);

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.recycler_item, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }
    @Override
    public int getItemCount() {
        return myRecyclerItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.latitudeTextView.setText(Double.toString(myRecyclerItems.get(position).getLatitude()));
        holder.longitudeTextView.setText(Double.toString(myRecyclerItems.get(position).getLongitude()));
        holder.addressTextView.setText(myRecyclerItems.get(position).getAddress());
    }
    public int getSize(){
        return myRecyclerItems.size();
    }
    public void removeItem(int pos){
        myRecyclerItems.remove(pos);
        notifyItemRemoved(0);
    }
    public void putItem(MyRecyclerItem item){
        myRecyclerItems.add(item);
        //notifyDataSetChanged();
        notifyItemInserted(myRecyclerItems.size()-1);
    }
}
