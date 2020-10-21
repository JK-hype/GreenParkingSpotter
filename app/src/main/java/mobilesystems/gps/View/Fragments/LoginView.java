package mobilesystems.gps.View.Fragments;

import android.os.Bundle;
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

public class LoginView extends Fragment {
    Button btn_login, btn_goToCreateAccount;
    EditText txt_username, txt_password;
    LoginViewModel loginVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_view, container, false);

        btn_login = view.findViewById(R.id.btn_login);
        btn_goToCreateAccount = view.findViewById(R.id.btn_goToCreateAccount);
        txt_username = view.findViewById(R.id.txt_username);
        txt_password = view.findViewById(R.id.txt_password);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginVM = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        loginVM.loginStatus().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginStatus) {
                if(loginStatus){
                    ParkingView parkingView = new ParkingView();
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();

                    fragmentTransaction.replace(R.id.mainFragment, parkingView);
                    fragmentTransaction.addToBackStack("LoginView");
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText(getContext(),"Login failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();
                if(!username.isEmpty() && !password.isEmpty()){
                    loginVM.login(username,password);
                }else{
                    Toast.makeText(getContext(), "You have not typed anything.", Toast.LENGTH_SHORT).show();
                }


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
