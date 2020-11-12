package mobilesystems.gps.View.Fragments;

import android.os.Bundle;
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
import mobilesystems.gps.ViewModel.CreateAccountViewModel;
import mobilesystems.gps.ViewModel.LoginViewModel;

public class CreateAccountView extends Fragment {
    CreateAccountViewModel VM;
    Button btn_create_account, btn_return;
    EditText txt_createStudentMail, txt_createPassword, txt_createCarType, txt_createCarBrand;
    RelativeLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.create_account_view, container, false);

        layout = view.findViewById(R.id.layout);
        btn_create_account = view.findViewById(R.id.btn_create_account);
        btn_return = view.findViewById(R.id.btn_return);
        txt_createStudentMail = view.findViewById(R.id.txt_createStudentMail);
        txt_createPassword = view.findViewById(R.id.txt_createPassword);
        txt_createCarType = view.findViewById(R.id.txt_createCarType);
        txt_createCarBrand = view.findViewById(R.id.txt_createCarBrand);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        VM = new ViewModelProvider(requireActivity()).get(CreateAccountViewModel.class);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Common.getInstance().hideKeyboard(getContext(), v, getActivity());
                txt_createStudentMail.clearFocus();
                txt_createPassword.clearFocus();
                txt_createCarType.clearFocus();
                txt_createCarBrand.clearFocus();
                return false;
            }
        });

        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VM.createAccount(txt_createStudentMail.getText().toString(), txt_createPassword.getText().toString(),
                                 txt_createCarType.getText().toString(), txt_createCarBrand.getText().toString(), getContext());
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginView loginView = new LoginView();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_fragments, loginView);
                fragmentTransaction.addToBackStack("LoginView");
                fragmentTransaction.commit();
            }
        });

        VM.createAccountStatus().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                if (status) {
                    Toast.makeText(getContext(), "Account created", Toast.LENGTH_SHORT).show();
                    LoginView loginView = new LoginView();
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container_fragments, loginView);
                    fragmentTransaction.addToBackStack("LoginView");
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(getContext(), "Something went wrong. Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
