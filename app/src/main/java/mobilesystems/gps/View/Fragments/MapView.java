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

import mobilesystems.gps.Acquaintance.IParkingLot;
import mobilesystems.gps.Acquaintance.IUser;
import mobilesystems.gps.Acquaintance.SessionData;
import mobilesystems.gps.R;
import mobilesystems.gps.ViewModel.LoginViewModel;
import mobilesystems.gps.ViewModel.MapViewModel;

public class MapView extends Fragment implements OnMapReadyCallback {

    private static final LatLng SDU_PARKING_COORDS1 = new LatLng(55.367575, 10.431397);
    private static final LatLng SDU_PARKING_COORDS2 = new LatLng(55.368611, 10.432463);
    private static final LatLngBounds SDU_PARKING_BOUNDS = new LatLngBounds(SDU_PARKING_COORDS1, SDU_PARKING_COORDS2);

    SupportMapFragment supportMapFragment;
    MapViewModel mapVM;
    GoogleMap map;

    List<LatLng> savedParkingLots;
    List<Marker> markers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.map_view, container, false);

        supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        savedParkingLots = new ArrayList<>();
        markers = new ArrayList<>();
        mapVM = new ViewModelProvider(requireActivity()).get(MapViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        supportMapFragment.getMapAsync(MapView.this);
    }

    private void setMapRequiredObservers() {
        mapVM.fetchParkingLotsCoordinates(getContext()).observe(requireActivity(), new Observer<List<IParkingLot>>() {
            @Override
            public void onChanged(List<IParkingLot> parkingLots) {
                IUser user = SessionData.getInstance().getCurrentUser();
                for (IParkingLot parkingLot : parkingLots) {
                    LatLng parkingLotCoords = new LatLng(parkingLot.getlatitude(), parkingLot.getlongitude());

                    if (!savedParkingLots.contains(parkingLotCoords)) {
                        savedParkingLots.add(parkingLotCoords);

                        Float color;
                        if (parkingLot.getAvailability()) {
                            color = BitmapDescriptorFactory.HUE_GREEN;
                        } else {
                            color = BitmapDescriptorFactory.HUE_RED;
                        }

                        MarkerOptions markerOptions = new MarkerOptions().position(parkingLotCoords).icon(BitmapDescriptorFactory.
                                defaultMarker(color));
                        markers.add(map.addMarker(markerOptions));
                    } else if (savedParkingLots.contains(parkingLotCoords)) {
                        for (Marker marker : markers) {
                            if (marker.getPosition().equals(parkingLotCoords)) {
                                if (parkingLot.getAvailability()) {
                                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                } else {
                                    if (parkingLotCoords == user.getLocation()) {
                                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                    } else {
                                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        mapVM.getLocationParked().observe(requireActivity(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                IParkingLot parkingLot = mapVM.calculateNearestMarker(location);
                if (parkingLot != null) {
                    LatLng coordinates = new LatLng(parkingLot.getlatitude(), parkingLot.getlongitude());
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 19.0f));
                    for (Marker marker : markers) {
                        if (marker.getPosition().equals(coordinates)) {
                            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            parkingLot.setAvailability(false);
                            SessionData.getInstance().setCurrentParkingLot(parkingLot);
                            mapVM.updateParkingLot(parkingLot, getContext());
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setLatLngBoundsForCameraTarget(SDU_PARKING_BOUNDS);
        map = googleMap;

        setMapRequiredObservers();

        checkPermission();
        googleMap.setMyLocationEnabled(true);

        if (SessionData.getInstance().getLocation() != null) {
            mapVM.setLocationParked(SessionData.getInstance().getLocation());
        }
    }

    private void checkPermission() {
        if (getContext() != null && getActivity() != null) {
            if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }
        }
    }
}
