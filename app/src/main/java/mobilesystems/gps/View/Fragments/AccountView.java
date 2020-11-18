package mobilesystems.gps.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import mobilesystems.gps.Acquaintance.IUser;
import mobilesystems.gps.Acquaintance.SessionData;
import mobilesystems.gps.R;

public class AccountView extends Fragment {

    TextView mail, password, carType, carBrand;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.account_view, container, false);

        mail = view.findViewById(R.id.textView_mailRes);
        password = view.findViewById(R.id.textView_passwordRes);
        carType = view.findViewById(R.id.textView_carTypeRes);
        carBrand = view.findViewById(R.id.textView_carBrandRes);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        IUser user = SessionData.getInstance().getCurrentUser();
        mail.setText(user.getStudent_mail());
        password.setText(user.getPassword());
        carType.setText(user.getCar_type());
        carBrand.setText(user.getCar_brand());
    }
}
