package mobilesystems.gps.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import mobilesystems.gps.Acquaintance.ICar;
import mobilesystems.gps.R;
import mobilesystems.gps.ViewModel.CarViewModel;

public class MenuView extends Fragment {

    Button btn_fetchCars;
    TextView carAmount, carBrand, carName, carHorsePower;
    CarViewModel carVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.car_view, container, false);

        btn_fetchCars = view.findViewById(R.id.btn_fetchCars);
        carAmount = view.findViewById(R.id.carAmount);
        carBrand = view.findViewById(R.id.carBrand);
        carName = view.findViewById(R.id.carName);
        carHorsePower = view.findViewById(R.id.carHP);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carVM = new ViewModelProvider(requireActivity()).get(CarViewModel.class);

        carVM.fetchCars().observe(requireActivity(), new Observer<List<ICar>>() {
            @Override
            public void onChanged(List<ICar> iCars) {
                for (ICar car : iCars) {
                    carAmount.setText("10");
                    carBrand.setText(car.getBrand());
                    carName.setText(car.getName());
                    carHorsePower.setText(car.getHorsePower());
                }
            }
        });

        btn_fetchCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carVM.fetchCars();
            }
        });
    }
}
