package mobilesystems.gps.View.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import mobilesystems.gps.R;

public class ParkingView extends Fragment {
    Button btn_park, btn_leave;
    View view_circle1, view_circle2, view_circle3, view_circle4, view_circle5, view_circle6, view_circle7;
    TextView current_lat, current_long;
    Boolean isParked;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.parking_view, container, false);

        btn_park = view.findViewById(R.id.btn_park);
        btn_leave = view.findViewById(R.id.btn_leave);
        view_circle1 = view.findViewById(R.id.view_circle1);
        view_circle2 = view.findViewById(R.id.view_circle2);
        view_circle3 = view.findViewById(R.id.view_circle3);
        view_circle4 = view.findViewById(R.id.view_circle4);
        view_circle5 = view.findViewById(R.id.view_circle5);
        view_circle6 = view.findViewById(R.id.view_circle6);
        view_circle7 = view.findViewById(R.id.view_circle7);

        current_lat = view.findViewById(R.id.txt_current_lat);
        current_long = view.findViewById(R.id.txt_current_long);

        isParked = false;
        btn_leave.setVisibility(View.GONE);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateLocation();

        btn_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isParked = true;
                updateLocation();
                Animation animation = new AlphaAnimation(0.0f, 1.0f);
                animation.setDuration(500); //You can manage the blinking time with this parameter
                animation.setStartOffset(20);
                //animation.setRepeatMode(Animation.REVERSE);
                //animation.setRepeatCount(Animation.INFINITE);
                btn_park.startAnimation(animation);
                btn_park.setText("PARKED");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_park.setVisibility(View.GONE);
                        btn_leave.setVisibility(View.VISIBLE);
                        btn_leave.setText("LEAVE");
                    }
                }, 5000); // Change to 1 minute? (60000)
            }
        });

        btn_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isParked = false;
                updateLocation();
                Animation animation = new AlphaAnimation(0.0f, 1.0f);
                animation.setDuration(500); //You can manage the blinking time with this parameter
                animation.setStartOffset(20);
                //animation.setRepeatMode(Animation.REVERSE);
                //animation.setRepeatCount(Animation.INFINITE);
                btn_leave.startAnimation(animation);
                btn_leave.setText("LEFT");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_park.setVisibility(View.VISIBLE);
                        btn_leave.setVisibility(View.GONE);
                        btn_park.setText("PARK");
                    }
                }, 5000); // Change to 1 minute? (60000)
            }
        });
    }

    private void updateLocation() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            while (!isParked) {
                                getLocation();
                            }
                            return null;
                        }
                    }.execute();
                }
            });
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

    private void getLocation() {
        checkPermission();
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(getContext(),
                                Locale.getDefault());
                        List<Address> addressList = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        current_lat.setText(String.valueOf(addressList.get(0).getLatitude()));
                        current_long.setText(String.valueOf(addressList.get(0).getLongitude()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
