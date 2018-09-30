package ie.app.heartwise.activities;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ie.app.heartwise.R;
import ie.app.heartwise.database.DatabaseHelper;
import ie.app.heartwise.model.Service;

import static ie.app.heartwise.R.id.map;

/**
 * Created by Ian on 20/04/2017.
 */

public class SearchScreen extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    DatabaseHelper helper;
    List<Service> serviceList;

    public static final List<LatLng> locations = new ArrayList<>();    //arrayList of LatLng locations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);
        helper = new DatabaseHelper(this);
        serviceList = helper.getDataFromServiceTable();
        for(Service s:serviceList){
            Double lat = Double.valueOf(s.getLatitude());     //populating the arrayList with coordinates
            Double lng = Double.valueOf(s.getLongitude());
            locations.add(new LatLng(lat,lng));
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        for (LatLng location: locations){

            this.googleMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("This AED has been serviced!")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.heartmarker)));
        }
    }

    public void onMapSearch(View view)
    {
        EditText locationSearch = (EditText) findViewById(R.id.editTextSearch);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            this.googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Location is here!")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.locationmarker)));
        }
    }

    public void onTypeNormal(View view)
    {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void onTypeHybrid(View view)
    {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public void onTypeSatellite(View view)
    {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void onZoom(View view)
    {
        if(view.getId() == R.id.zoomIn)
        {
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if(view.getId() == R.id.zoomOut)
        {
            googleMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }
}
