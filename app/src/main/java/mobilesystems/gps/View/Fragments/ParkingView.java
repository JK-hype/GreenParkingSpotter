package mobilesystems.gps.View.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import mobilesystems.gps.R;
import mobilesystems.gps.View.Activities.NavigationDrawerMenu;

public class ParkingView extends Fragment {
    Button btn_park, btn_leave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.parking_view, container, false);

        btn_park = view.findViewById(R.id.btn_park);
        btn_leave = view.findViewById(R.id.btn_leave);

        btn_leave.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

}
