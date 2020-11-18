package mobilesystems.gps.View.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mobilesystems.gps.R;
import mobilesystems.gps.ViewModel.MapViewModel;

public class MapView extends Fragment implements OnMapReadyCallback {

    private static final LatLng SDU_PARKING_COORDS1 = new LatLng(55.367575, 10.431397);
    private static final LatLng SDU_PARKING_COORDS2 = new LatLng(55.368611, 10.432463);
    private static final LatLngBounds SDU_PARKING_BOUNDS = new LatLngBounds(SDU_PARKING_COORDS1, SDU_PARKING_COORDS2);
    private static final int REQUEST_CODE = 101;
    private static final String TAG = "MapView";
    private int index = 1000;
    private boolean isFirstTime = true;

    SupportMapFragment supportMapFragment;
    MapViewModel mapVM = new MapViewModel();
    Location carLocation;
    GoogleMap map;

    List<Marker> markers = new ArrayList<>();
    List<LatLng> parkingLotsCoords = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView called");
        final View view = inflater.inflate(R.layout.map_view, container, false);

        supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle b = this.getArguments();

        if (b != null) {
            carLocation = (Location) b.getParcelable("location");
        }

        supportMapFragment.getMapAsync(MapView.this);
        mapVM = new ViewModelProvider(requireActivity()).get(MapViewModel.class);

        mapVM.fetchParkingLotsCoordinates().observe(requireActivity(), new Observer<List<LatLng>>() {
            @Override
            public void onChanged(List<LatLng> latLngs) {
                Log.i(TAG, "change in fetchCoordinates");
                parkingLotsCoords = latLngs;
                if (latLngs != null && isFirstTime) {
                    Log.i(TAG, "Map Async");
                    createMarkers(map);
                    isFirstTime = false;
                }

            }
        });

        mapVM.fetchColors().observe(requireActivity(), new Observer<List<Boolean>>() {
            @Override
            public void onChanged(List<Boolean> booleans) {
                Log.i(TAG, "change in fetchColors()");
                spotColor(booleans);
            }
        });

        mapVM.getNearestMarker(carLocation).observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.i(TAG, "Change in getNearestMarker");
                if (integer != null) {
                    index = integer;
                    markMySpot(map);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "onMapReady called");

        googleMap.setLatLngBoundsForCameraTarget(SDU_PARKING_BOUNDS);

        map = googleMap;

        //googleMap.setMyLocationEnabled(true);
        Log.i(TAG, "MyLocation enabled");
    }

    public void spotColor(List<Boolean> colorsOfSpot) {
        Log.i(TAG, "spotColor called");
        for (int i = 0; i < colorsOfSpot.size(); i++) {
            if (i != index) {
                if (!colorsOfSpot.get(i)) {
                    markers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                } else {
                    markers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }
            }
        }
        Log.i(TAG, "spotColor set");
    }

    public void createMarkers(GoogleMap googleMap) {
        Log.i(TAG, "createMarkers called");
        for (LatLng coords : parkingLotsCoords) {
            MarkerOptions markerOptions = new MarkerOptions().position(coords).icon(BitmapDescriptorFactory.
                    defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            markers.add(googleMap.addMarker(markerOptions));
        }
    }

    public void markMySpot(GoogleMap googleMap) {
        Log.i(TAG, "markMySpot called");
        LatLng latLng = new LatLng(parkingLotsCoords.get(0).latitude, parkingLotsCoords.get(0).longitude);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19.0f));
        markers.get(index).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
    }
}
