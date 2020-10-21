package mobilesystems.gps.Model.Repository;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Model.Adapters.CarAdapter;

public class CarService {
    public void fetchCars(final Callback callback) {
        CarAdapter carAdapter = new CarAdapter();
        callback.onResponse(carAdapter.convertCars(10, "Audi", "TT", "9001"));
    }
}
