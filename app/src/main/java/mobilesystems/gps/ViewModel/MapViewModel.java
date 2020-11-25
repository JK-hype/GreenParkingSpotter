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
    MutableLiveData<List<IParkingLot>> allParkingLots;
    MutableLiveData<Location> locationParked;

    public LiveData<List<IParkingLot>> fetchParkingLotsCoordinates(Context c) {
        if (allParkingLots == null) {
            allParkingLots = new MutableLiveData<>();
        }
        fetchCoordinatesFromModel(c);
        return allParkingLots;
    }

    private void fetchCoordinatesFromModel(Context c) {
        mapCoordinateService.fetchCoordinates(new Callback() {
            @Override
            public void onResponse(Object o) {
                allParkingLots.postValue((List<IParkingLot>) o);
            }
        }, c);
    }

    public void updateParkingLot(IParkingLot parkingLot, Context c) {
        mapCoordinateService.updateParkingLot(parkingLot, c);
    }

    public LiveData<Location> getLocationParked() {
        if (locationParked == null) {
            locationParked = new MutableLiveData<>();
        }
        return locationParked;
    }

    public void setLocationParked(Location location) {
        if (locationParked == null) {
            locationParked = new MutableLiveData<>();
        }
        locationParked.postValue(location);
    }

    public IParkingLot calculateNearestMarker(Location location) {
        if (location == null) {
            return null;
        }

        if (allParkingLots.getValue() == null) {
            return null;
        }

        float min = Float.MAX_VALUE;
        float[] results = new float[3];
        IParkingLot returnParkingLot = null;

        for (IParkingLot parkingLot : allParkingLots.getValue()) {
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
        return returnParkingLot;
    }

}
