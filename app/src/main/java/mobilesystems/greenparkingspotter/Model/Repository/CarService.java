package mobilesystems.greenparkingspotter.Model.Repository;

import mobilesystems.greenparkingspotter.Acquaintance.Callback;
import mobilesystems.greenparkingspotter.Model.Adapters.CarAdapter;

public class CarService {
    public void fetchCars(final Callback callback) {
        CarAdapter carAdapter = new CarAdapter();
        callback.onResponse(carAdapter.convertCars(10, "Audi", "TT", "9001"));
    }
}
