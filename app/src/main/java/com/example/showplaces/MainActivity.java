package com.example.showplaces;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.showplaces.Model.ShowItem;
import com.example.showplaces.databinding.ActivityMainBinding;
import com.example.showplaces.ui.main.SectionsPagerAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Pulled places
    public static final ArrayList<ShowItem> pulledPlaces = new ArrayList<>();

    //MarkerMap
    public static final HashMap<String, ShowItem> markerMap = new HashMap<>();

    //MapType
    public static int mapType = GoogleMap.MAP_TYPE_NORMAL;

    //List Sort Order
    public static int sortOrder = 0;

    //Preferred Maps App
    public static int preferredMapsApp = 0;

    //Map Pin Color (Hex)
    public static String pinColor = "#FFFFFF";

    //Filters
    public static final ArrayList<String> filters = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.showplaces.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if( this.getApplicationContext().checkSelfPermission( Manifest.permission.READ_CONTACTS ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }

        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
                // ask permissions here using below code
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }
        //TODO: Read the contacts into a class here. Then, access pulledPlaces via requireActivity()

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);





    }


}