package org.androidtown.myapplication;

/**
 * Created by CYSN on 2017-10-10.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    //ViewHolder viewHolder;
    //ViewHolder viewHolder2;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView longiTextView;
        public TextView latitudeTextView;
        public TextView adressTextView;

        public ContactsAdapter mContacts;

        public Context context;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(Context context, View itemView, ContactsAdapter contacts) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            longiTextView = (TextView) itemView.findViewById(R.id.contact_longitude);
            latitudeTextView = (TextView) itemView.findViewById(R.id.contact_latitude);
            adressTextView = (TextView) itemView.findViewById(R.id.contact_address);

            this.context = context;
            this.mContacts = contacts;


        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            // We can access the data within the views
            //Toast.makeText(context, nameTextView.getText() + Integer.toString(position), Toast.LENGTH_SHORT).show();
            //mContacts.removeItem(position);

        }
    }


    // Store a member variable for the contacts
    private ArrayList<Contact> mContacts;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public ContactsAdapter(Context context, ArrayList<Contact> contacts) {
        mContacts = contacts;
        mContext = context;

    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }


    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_contact, parent, false);


        ViewHolder viewHolder = new ViewHolder(context, contactView, this);


        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Contact contact = mContacts.get(position);

        // Set item views based on your views and data model
        TextView adresstextView = viewHolder.adressTextView;
        TextView longitudetextView = viewHolder.longiTextView;
        TextView latitudeTextView = viewHolder.latitudeTextView;

        adresstextView.setText(contact.getAddress());
        longitudetextView.setText(Double.toString(contact.getLongitude()));
        latitudeTextView.setText(Double.toString(contact.getLatitude()));

    }


    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public void removeItem(int p) {
        mContacts.remove(p);
        notifyItemRemoved(p);

    }

}
