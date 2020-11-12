package mobilesystems.gps.View.Fragments;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import mobilesystems.gps.Acquaintance.Common;
import mobilesystems.gps.R;
import mobilesystems.gps.View.Activities.NavigationDrawerMenu;
import mobilesystems.gps.ViewModel.LoginViewModel;

public class LoginView extends Fragment {
    Button btn_login, btn_goToCreateAccount;
    EditText txt_studentMail, txt_password;
    LoginViewModel loginVM;
    RelativeLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_view, container, false);

        layout = view.findViewById(R.id.layout);
        btn_login = view.findViewById(R.id.btn_login);
        btn_goToCreateAccount = view.findViewById(R.id.btn_goToCreateAccount);
        txt_studentMail = view.findViewById(R.id.txt_studentMail);
        txt_password = view.findViewById(R.id.txt_password);

        // Locking the Navigation Drawer on the Login View
        ((NavigationDrawerMenu) getActivity()).setDrawerLocked(true);
        ((NavigationDrawerMenu) getActivity()).setToolbarVisibility(false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginVM = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        loginVM.loginStatus().observe(requireActivity(), new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> status) {
                if (status.first) {
                    ParkingView parkingView = new ParkingView();
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container_fragments, parkingView);
                    fragmentTransaction.commit();

                    ((NavigationDrawerMenu) getActivity()).setItemPark();
                    ((NavigationDrawerMenu) getActivity()).setDrawerLocked(false);
                    ((NavigationDrawerMenu) getActivity()).setToolbarVisibility(true);
                } else {
                    Toast.makeText(getContext(), status.second, Toast.LENGTH_SHORT).show();
                }
            }
        });

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Common.getInstance().hideKeyboard(getContext(), v, getActivity());
                txt_studentMail.clearFocus();
                txt_password.clearFocus();
                return false;
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = txt_studentMail.getText().toString().trim();
                String password = txt_password.getText().toString().trim();
                if (!mail.isEmpty() && !password.isEmpty()) {
                    loginVM.login(mail, password, getContext());
                } else {
                    Toast.makeText(getContext(), "Please type in mail and password.", Toast.LENGTH_LONG).show();
                }
                Common.getInstance().hideKeyboard(getContext(), getView(), getActivity());
            }
        });

        btn_goToCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccountView createAccountView = new CreateAccountView();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_fragments, createAccountView);
                fragmentTransaction.addToBackStack("CreateAccountView");
                fragmentTransaction.commit();
            }
        });
    }
}
