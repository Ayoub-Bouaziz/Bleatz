package fr.stvenchg.bleatz.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import fr.stvenchg.bleatz.R;

public class RestaurantLocationFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    // TODO: Replace these coordinates with your restaurant's coordinates
    private static final LatLng RESTAURANT_LOCATION = new LatLng(48.8566, 2.3522);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_location, container, false);

        // Get the SupportMapFragment and request notification when the map is ready to be used
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker for the restaurant location and move the camera
        mMap.addMarker(new MarkerOptions().position(RESTAURANT_LOCATION).title("Restaurant"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(RESTAURANT_LOCATION, 16));
    }
}