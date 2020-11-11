package mobilesystems.gps.View.Fragments;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import mobilesystems.gps.R;
import mobilesystems.gps.ViewModel.MapViewModel;

public class MapView extends Fragment implements OnMapReadyCallback {

    private static final LatLng LAT_LNG_1 = new LatLng( 55.367575, 10.431397 );
    private static final LatLng LAT_LNG_2 = new LatLng( 55.368611, 10.432463 );
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(LAT_LNG_1, LAT_LNG_2);
    private int index;

    Button BtnTest;
    Button BtnUpdate;
    SupportMapFragment supportMapFragment;
    MapViewModel mapVM;
    Location carLocation;
    GoogleMap map;


    List<Marker> markers = new ArrayList<>();
    List<Boolean> colorOfSpot = new ArrayList<>();
    List<LatLng> coordinates = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.map_view,container,false);

        BtnUpdate = view.findViewById(R.id.btn_update);
        supportMapFragment = (SupportMapFragment) getParentFragment();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapVM.fetchCarLocation();
            }
        });

        mapVM = new ViewModelProvider(requireActivity()).get(MapViewModel.class);

        mapVM.getNearestMarker().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer != null){
                    index = integer;
                    markMySpot(map);
                }

            }
        });

        mapVM.fetchCoordinates().observe(requireActivity(), new Observer<List<LatLng>>() {
            @Override
            public void onChanged(List<LatLng> latLngs) {
                if(latLngs != null){
                    coordinates = latLngs;
                    spotColor();
                }
            }
        });

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setLatLngBoundsForCameraTarget(LAT_LNG_BOUNDS);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);

        map = googleMap;

        googleMap.setMyLocationEnabled(true);

        createMarkers(googleMap);
    }

    public void spotColor(){
        for(int i = 0; i < colorOfSpot.size(); i++){
            if(i != index){
                if(!colorOfSpot.get(i)){
                    markers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }else{
                    markers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }
            }
        }
    }

    public void createMarkers(GoogleMap googleMap){
        for(LatLng latLng : coordinates){
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.
                    defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            markers.add(googleMap.addMarker(markerOptions));
        }
    }

    public void markMySpot(GoogleMap googleMap){
        LatLng latLng = new LatLng(markers.get(index).getPosition().latitude,markers.get(index).getPosition().longitude);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,19.0f));
        markers.get(index).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
    }
}
