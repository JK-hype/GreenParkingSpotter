package mobilesystems.gps.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Acquaintance.ICar;
import mobilesystems.gps.Model.Repository.CarService;

public class CarViewModel extends ViewModel {
    MutableLiveData<List<ICar>> carList;
    CarService carService = new CarService();

    public LiveData<List<ICar>> fetchCars() {
        if (carList == null) {
            carList = new MutableLiveData<>();
        }
        fetchCarsFromModel();
        return carList;
    }

    private void fetchCarsFromModel() {
        carService.fetchCars(new Callback() {
            @Override
            public void onResponse(Object o) {
                if (carList == null) {
                    carList = new MutableLiveData<>();
                }
                carList.postValue((List<ICar>) o);
            }
        });
    }
}
