package com.example.showplaces.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.showplaces.Model.ShowItem;
import com.example.showplaces.R;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_showplace_display, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        ShowItem item = mContacts.get(position);

        TextView textView = holder.nameTextView;
        textView.setText(item.name);
        TextView textView1 = holder.labelTextView;
        textView1.setText("| " + item.label);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView labelTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.list_item_name);
            labelTextView = itemView.findViewById(R.id.list_item_label);
        }
    }

    private List<ShowItem> mContacts;

    public ContactsAdapter(List<ShowItem> contacts) {
        mContacts = contacts;
    }
    
}
