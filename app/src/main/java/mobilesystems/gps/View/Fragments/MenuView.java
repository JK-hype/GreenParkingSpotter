package mobilesystems.gps.View.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import mobilesystems.gps.R;
import mobilesystems.gps.ViewModel.LoginViewModel;

public class MenuView extends Fragment {
    Button btn_park, btn_map, btn_perks, btn_account, btn_about;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.menu_view, container, false);

        btn_park = view.findViewById(R.id.btn_park);
        btn_map = view.findViewById(R.id.btn_map);
        btn_perks = view.findViewById(R.id.btn_perks);
        btn_account = view.findViewById(R.id.btn_account);
        btn_about = view.findViewById(R.id.btn_about);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParkingView parkingView = new ParkingView();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.mainFragment, parkingView);
                fragmentTransaction.addToBackStack("ParkingView");
                fragmentTransaction.commit();
            }
        });

        // Just to test the functionality of the menu
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginView loginView = new LoginView();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.mainFragment, loginView);
                fragmentTransaction.addToBackStack("LoginView");
                fragmentTransaction.commit();
            }
        });

        /*
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccountView createAccountView = new CreateAccountView();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.mainFragment, createAccountView);
                fragmentTransaction.addToBackStack("CreateAccountView");
                fragmentTransaction.commit();
            }
        });

        btn_perks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccountView createAccountView = new CreateAccountView();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.mainFragment, createAccountView);
                fragmentTransaction.addToBackStack("CreateAccountView");
                fragmentTransaction.commit();
            }
        });

        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccountView createAccountView = new CreateAccountView();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.mainFragment, createAccountView);
                fragmentTransaction.addToBackStack("CreateAccountView");
                fragmentTransaction.commit();
            }
        });

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccountView createAccountView = new CreateAccountView();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.mainFragment, createAccountView);
                fragmentTransaction.addToBackStack("CreateAccountView");
                fragmentTransaction.commit();
            }
        });*/
    }

}
