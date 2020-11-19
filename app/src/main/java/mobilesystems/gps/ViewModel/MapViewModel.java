package mobilesystems.gps.ViewModel;

import android.content.Context;
import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Acquaintance.IParkingLot;
import mobilesystems.gps.Model.Repository.MapCoordinateService;

public class MapViewModel extends ViewModel {

    MapCoordinateService mapCoordinateService = new MapCoordinateService();
    MutableLiveData<IParkingLot> UserParkingLot;
    MutableLiveData<List<IParkingLot>> coordinates;

    public LiveData<List<IParkingLot>> fetchParkingLotsCoordinates(Context c) {
        if (coordinates == null) {
            coordinates = new MutableLiveData<>();
        }
        fetchCoordinatesFromModel(c);
        return coordinates;
    }

    private void fetchCoordinatesFromModel(Context c) {
        mapCoordinateService.fetchCoordinates(new Callback() {
            @Override
            public void onResponse(Object o) {
                coordinates.postValue((List<IParkingLot>) o);
            }
        }, c);
    }

    public void updateParkingLot(IParkingLot parkingLot, Context c) {
        mapCoordinateService.updateParkingLot(parkingLot, c);
    }

    public LiveData<IParkingLot> getNearestMarker(Location location) {
        if (UserParkingLot == null) {
            UserParkingLot = new MutableLiveData<>();
        }
        calculateNearestMarker(location);
        return UserParkingLot;
    }

    private void calculateNearestMarker(Location location) {
        float min = Float.MAX_VALUE;
        float[] results = new float[3];
        IParkingLot returnParkingLot = null;

        if (coordinates.getValue() == null) {
            return;
        }

        for (IParkingLot parkingLot : coordinates.getValue()) {
            Location.distanceBetween(parkingLot.getlatitude(),
                    parkingLot.getlongitude(),
                    location.getLatitude(),
                    location.getLongitude(),
                    results);
            if (results[0] < min) {
                min = results[0];
                returnParkingLot = parkingLot;
            }
        }
        UserParkingLot.postValue(returnParkingLot);
    }

}
