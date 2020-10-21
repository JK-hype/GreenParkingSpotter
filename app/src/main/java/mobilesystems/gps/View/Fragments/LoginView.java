package mobilesystems.gps.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import mobilesystems.gps.R;

public class LoginView extends Fragment {
    Button btn_login, btn_goToCreateAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_view, container, false);

        btn_login = view.findViewById(R.id.btn_login);
        btn_goToCreateAccount = view.findViewById(R.id.btn_goToCreateAccount);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParkingView parkingView = new ParkingView();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.mainFragment, parkingView);
                fragmentTransaction.addToBackStack("LoginView");
                fragmentTransaction.commit();
        }
        });

        btn_goToCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccountView createAccountView = new CreateAccountView();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.mainFragment, createAccountView);
                fragmentTransaction.addToBackStack("CreateAccountView");
                fragmentTransaction.commit();
            }
        });
    }

}
