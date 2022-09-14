package com.example.showplaces.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.showplaces.MainActivity;
import com.example.showplaces.R;
import com.example.showplaces.util.adapters.ContactsAdapter;

public class ShowList extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";


    public static ShowList newInstance(int index) {
        ShowList fragment = new ShowList();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PageViewModel pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 2;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @SuppressLint("PotentialBehaviorOverride")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view=inflater.inflate(R.layout.list_tab, container, false);

        //Initialize Both Buttons, the "contacts" text, and the recyclerview


        TextView centerText = view.findViewById(R.id.textView3);
        centerText.setText("Contacts: " + MainActivity.pulledPlaces.size());

        RecyclerView recyclerView = view.findViewById(R.id.contacts_recycler);
        ContactsAdapter adapter = new ContactsAdapter(MainActivity.pulledPlaces);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        return view;
    }
}
