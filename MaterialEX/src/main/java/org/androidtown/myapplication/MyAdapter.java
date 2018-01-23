package org.androidtown.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by CYSN on 2017-09-26.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Contact> arrayList;
    public MyAdapter(Context context, ArrayList<Contact> arrayList){
        this.arrayList = arrayList;
    }
    public static class ViewHolder0 extends RecyclerView.ViewHolder implements  View.OnClickListener {
        public Button mbtn;
        public TextView mtextView;
        public Context context;
        public MyAdapter myRecyclerAdapter;

        public ViewHolder0(Context context, View itemView, MyAdapter myRecyclerAdapter) {
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
    public static class ViewHolder1 extends RecyclerView.ViewHolder implements  View.OnClickListener {
        public Button mbtn;
        public TextView mtextView;
        public TextView mtextView2;
        public Context context;
        public MyAdapter myRecyclerAdapter;

        public ViewHolder1(Context context, View itemView, MyAdapter myRecyclerAdapter) {
            super(itemView);
            this.mbtn = (Button) itemView.findViewById(R.id.deleteBtn);
            this.mtextView = (TextView) itemView.findViewById(R.id.nameTextView);
            this.mtextView2 = (TextView) itemView.findViewById(R.id.nameTextView2);
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder0){//position이 0인지 2인지로 걸어도됨 getItemViewType
            Contact contact = arrayList.get(position);
            TextView view = ((ViewHolder0)holder).mtextView;
            view.setText(contact.getName());
            Button btn = ((ViewHolder0)holder).mbtn;
            btn.setText("Message");
        }
        else{
            Contact contact = arrayList.get(position);
            TextView view = ((ViewHolder1)holder).mtextView;
            TextView view2 = ((ViewHolder1)holder).mtextView2;
            view.setText(contact.getName());
            Button btn = ((ViewHolder1)holder).mbtn;
            view2.setText("kkkkk");
            btn.setText("Message");
        }

    }

    @Override
    public int getItemViewType(int position) {  // position에 해당하는 viewtype을 리턴
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 2 * 2;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public void removeItem(int p) {
        arrayList.remove(p);
        notifyItemRemoved(p);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:{
                Context context = parent.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.item,parent,false);
                MyAdapter.ViewHolder0 viewHolder = new  MyAdapter.ViewHolder0(context, view, this);
                return viewHolder;
            }
            case 2:{
                Context context = parent.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.item1,parent,false);
                MyAdapter.ViewHolder1 viewHolder = new  MyAdapter.ViewHolder1(context, view, this);
                return viewHolder;
            }   //2번 뷰타입
        }
        return null;
    }
}