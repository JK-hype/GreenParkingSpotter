package mobilesystems.gps.View.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import mobilesystems.gps.R;
import mobilesystems.gps.ViewModel.MapViewModel;

public class MapView extends Fragment implements OnMapReadyCallback {

    private static final LatLng LAT_LNG_1 = new LatLng( 55.367575, 10.431397 );
    private static final LatLng LAT_LNG_2 = new LatLng( 55.368611, 10.432463 );
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(LAT_LNG_1, LAT_LNG_2);
    private static final int REQUEST_CODE = 101;
    private static final String TAG = "MapView";
    private int index = 1000;
    private boolean isFirstTime = true;

    Button BtnUpdate;
    SupportMapFragment supportMapFragment;
    MapViewModel mapVM = new MapViewModel();
    Location carLocation;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    Activity activity;

    List<Marker> markers = new ArrayList<>();
    List<LatLng> coordinates = new ArrayList<>();

    public MapView(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView called");
        final View view = inflater.inflate(R.layout.map_view,container,false);

        BtnUpdate = view.findViewById(R.id.btn_update);
        supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated called");
        super.onViewCreated(view, savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        supportMapFragment.getMapAsync(MapView.this);

        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchCarLocation();
            }
        });

        mapVM = new ViewModelProvider(requireActivity()).get(MapViewModel.class);


        mapVM.fetchCoordinates().observe(requireActivity(), new Observer<List<LatLng>>() {
            @Override
            public void onChanged(List<LatLng> latLngs) {
                Log.i(TAG, "change in fetchCoordinates");
                coordinates = latLngs;
                if(latLngs != null && isFirstTime) {
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

        fetchCarLocation();

        mapVM.getNearestMarker(carLocation).observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.i(TAG, "Change in getNearestMarker");
                if(integer != null){
                    index = integer;
                    markMySpot(map);
                }

            }
        });

    }

    public void fetchCarLocation() {
        Log.i(TAG, "fetchCarLocation called");

        checkPermission();
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    Log.i(TAG, "successfully get last location ");
                    carLocation = location;
                    mapVM.getNearestMarker(location);
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "onMapReady called");

        googleMap.setLatLngBoundsForCameraTarget(LAT_LNG_BOUNDS);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);

        map = googleMap;

        checkPermission();
        googleMap.setMyLocationEnabled(true);
        Log.i(TAG, "MyLocation enabled");


    }

    public void spotColor(List<Boolean> colorsOfSpot){
        Log.i(TAG, "spotColor called");
        for(int i = 0; i < colorsOfSpot.size(); i++){
            if(i != index){
                if(!colorsOfSpot.get(i)){
                    markers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }else{
                    markers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }
            }
        }
        Log.i(TAG, "spotColor set");
    }

    public void createMarkers(GoogleMap googleMap){
        Log.i(TAG, "createMarkers called");
        for(LatLng latLng : coordinates){
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.
                    defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            markers.add(googleMap.addMarker(markerOptions));
        }
    }

    public void markMySpot(GoogleMap googleMap){
        Log.i(TAG, "markMySpot called");
        LatLng latLng = new LatLng(markers.get(index).getPosition().latitude,markers.get(index).getPosition().longitude);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,19.0f));
        markers.get(index).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
    }

    public void checkPermission(){
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (activity,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG,"onRequestPermissionResult: checking permission results");
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    break;
                }else{
                    Toast.makeText(getContext(), "Permission for location was not granted.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
