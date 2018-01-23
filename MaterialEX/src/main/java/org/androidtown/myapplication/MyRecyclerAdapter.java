package org.androidtown.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by CYSN on 2017-09-26.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    ArrayList<Contact> arrayList;
    public static class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        public Button mbtn;
        public TextView mtextView;
        public Context context;
        public MyRecyclerAdapter myRecyclerAdapter;
            ViewHolder(Context context, View itemView,MyRecyclerAdapter myRecyclerAdapter){
                super(itemView);
                this.mbtn = (Button) itemView.findViewById(R.id.deleteBtn);
                this.mtextView = (TextView) itemView.findViewById(R.id.nameTextView);
                this.context = context;
                this.myRecyclerAdapter = myRecyclerAdapter;
                mbtn.setOnClickListener(this);
            }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            myRecyclerAdapter.removeItem(position);
            Toast.makeText(context, mtextView.getText() + Integer.toString(position),
                    Toast.LENGTH_SHORT).show();
        }
    }
    public MyRecyclerAdapter(Context context, ArrayList<Contact> arrayList){
        this.arrayList = arrayList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item,parent,false);
        ViewHolder viewHolder = new ViewHolder(context, view, this);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.ViewHolder holder, int position) {
        Contact contact = arrayList.get(position);
        TextView view = holder.mtextView;
        view.setText(contact.getName());
        Button btn = holder.mbtn;
        btn.setText("Message");
    }
    public void removeItem(int p) {
        arrayList.remove(p);
        notifyItemRemoved(p);
    }
}
