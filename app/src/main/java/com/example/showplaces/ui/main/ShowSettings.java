package com.example.showplaces.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.showplaces.MainActivity;
import com.example.showplaces.R;
import com.google.android.gms.maps.GoogleMap;

public class ShowSettings extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";


    public static ShowSettings newInstance(int index) {
        ShowSettings fragment = new ShowSettings();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PageViewModel pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 3;
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
        View view=inflater.inflate(R.layout.settings_tab, container, false);

        Button button = view.findViewById(R.id.mapTypeSettings);

        button.setOnClickListener(view1 -> {
            // Initializing the popup menu and giving the reference as current context
            PopupMenu popupMenu = new PopupMenu(requireContext(), button);

            // Inflating popup menu from popup_menu.xml file
            popupMenu.getMenuInflater().inflate(R.menu.gmap_type_popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                // Toast message on menu item clicked
                if(menuItem.getTitle().equals("Satellite")) {
                    MainActivity.mapType = GoogleMap.MAP_TYPE_SATELLITE;
                }
                else if(menuItem.getTitle().equals("Hybrid")) {
                    MainActivity.mapType = GoogleMap.MAP_TYPE_HYBRID;
                }
                else {
                    MainActivity.mapType = GoogleMap.MAP_TYPE_NORMAL;
                }
                ShowMap.mMap.setMapType(MainActivity.mapType);
                button.setText(menuItem.getTitle());
                return true;
            });
            // Showing the popup menu
            popupMenu.show();
        });
        //Initialize Both Buttons, the "contacts" text, and the recyclerview


        return view;
    }
}
