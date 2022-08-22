package com.example.showplaces.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.showplaces.Model.ShowContact;
import com.example.showplaces.Model.ShowItem;
import com.example.showplaces.Model.ShowPlace;
import com.example.showplaces.R;
import com.example.showplaces.util.ContactsAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ShowMap extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private final ArrayList<ShowItem> pulledPlaces = new ArrayList<>();
    private final HashMap<String, ShowItem> markerMap = new HashMap<>();

    private LocationManager locationManager;

    public static ShowMap newInstance(int index) {
        ShowMap fragment = new ShowMap();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if( requireContext().getApplicationContext().checkSelfPermission( Manifest.permission.READ_CONTACTS ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.READ_CONTACTS},1);
        }
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        readContacts();


        PageViewModel pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
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
        View view=inflater.inflate(R.layout.show_map, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        assert supportMapFragment != null;




        supportMapFragment.getMapAsync(googleMap -> {
            if (ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                int permissionCheck = ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    // ask permissions here using below code
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                }

                return;
            }
            googleMap.setMyLocationEnabled(true);


            Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(currentLocation == null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pulledPlaces.get(0).coordinates, 10));
                System.out.println("Current location is null");
            }
            else {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 10));
            }



            for(int i = 0; i < pulledPlaces.size(); i++) {
                LatLng latLng = pulledPlaces.get(i).coordinates;
                Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng).title(pulledPlaces.get(i).name).snippet(pulledPlaces.get(i).address));
                assert marker != null;
                String id = marker.getId();
                markerMap.put(id, pulledPlaces.get(i));
            }

            googleMap.setOnInfoWindowClickListener(marker -> {
                ShowPlace place = (ShowPlace) markerMap.get(marker.getId());

                //TODO: Bring up the info lmao

            });

            FloatingActionButton fab = requireActivity().findViewById(R.id.fab);
            fab.setOnClickListener(view1 -> {
                assert currentLocation != null;
                ShowPlace placeToAdd = new ShowPlace("Test Location", "Home", "ShowPlace");
                placeToAdd.setCoordinates(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                Marker marker = googleMap.addMarker(new MarkerOptions().position(placeToAdd.coordinates).title(placeToAdd.name).snippet(placeToAdd.address));
                assert marker != null;
                markerMap.put(marker.getId(), placeToAdd);
            });

        });

        //Do Shite
        // Return view
//        RecyclerView recyclerView = (RecyclerView) requireActivity().findViewById(R.id.contacts_recycler);
//        ContactsAdapter adapter = new ContactsAdapter(pulledPlaces);
//
//        recyclerView.setAdapter(adapter);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    public void readContacts() {
        StringBuilder sb = new StringBuilder();
        sb.append("......Contact Details.....");

        ContentResolver cr = requireContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        String phone;
        String emailContact;
        String emailType;
        String image_uri;
        Bitmap bitmap = null;
        String address = null;
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur
                        .getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String name = cur
                        .getString(cur
                                .getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                image_uri = cur
                        .getString(cur
                                .getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                if (Integer
                        .parseInt(cur.getString(cur
                                .getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    sb.append("\n Contact Name:").append(name);
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = ?", new String[] { id }, null);
                    while (pCur.moveToNext()) {
                        phone = pCur
                                .getString(pCur
                                        .getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        sb.append("\n Phone number:").append(phone);
                    }
                    pCur.close();

                    Cursor emailCur = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID
                                    + " = ?", new String[] { id }, null);
                    while (emailCur.moveToNext()) {
                        emailContact = emailCur
                                .getString(emailCur
                                        .getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.DATA));
                        emailType = emailCur
                                .getString(emailCur
                                        .getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.TYPE));
                        sb.append("\nEmail:").append(emailContact).append("Email type:").append(emailType);

                    }

                    emailCur.close();

                    Cursor addressCur = cr.query(
                            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                            null, ContactsContract.Data.CONTACT_ID + "= ?", new String[] { id }, null);
                    while(addressCur.moveToNext())
                    {

                        String addressStreet = addressCur.getString(addressCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                        String addressCity = addressCur.getString(addressCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                        String addressCountry = addressCur.getString(addressCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                        String addressState = addressCur.getString(addressCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                        if(Objects.equals(addressCountry, null)) {
                            address = addressStreet + ", " + addressCity + ", " + addressState;
                        }
                        else {
                            address = addressStreet + ", " + addressCity + ", " + addressState + ", " + addressCountry;
                        }

                        sb.append("\nAddress: ").append(address);
                    }
                    addressCur.close();
                }

                if (image_uri != null) {
                    System.out.println(Uri.parse(image_uri));
                    try {
                        bitmap = MediaStore.Images.Media
                                .getBitmap(this.requireContext().getContentResolver(),
                                        Uri.parse(image_uri));
                        sb.append("\n Image in Bitmap:").append(bitmap);
                        System.out.println(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if(address != null) {

                    ShowContact place = new ShowContact(address, "Home",name);
                    place.address = address;
                    System.out.println("Name added: " + name + ", Address Added: " + address);
                    address = null;
                    if(bitmap != null) {
                        place.icon = bitmap;
                    }
                    pulledPlaces.add(place);
                }


                sb.append("\n........................................");
            }
            System.out.println("Number of locations: " + pulledPlaces.size());
            for(ShowItem place : pulledPlaces) {
                geocodeAddress(place);
            }
        }
        cur.close();

        //TODO: Compare pulled vs reopened locations, if the same, use reopened locations which have lat and lon, else use geocoder.

    }

    public void geocodeAddress(ShowItem address) {


        Geocoder geocoder = new Geocoder(this.requireContext());
        Address location = null;

        List<Address> foundLocations = new ArrayList<>();
        try {
            foundLocations = geocoder.getFromLocationName(address.address, 3);
            if(foundLocations.size() > 1) {
                System.out.println("WARNING!    " + foundLocations.size() + "   LOCATIONS FOUND");
            }
        }
        catch(IOException ignored) { }

        if(foundLocations.size() > 0) {
            location = foundLocations.get(0);
        }

        if(location != null) {
            System.out.println(location.getLatitude() + "," + location.getLongitude());
            address.setCoordinates(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }
}