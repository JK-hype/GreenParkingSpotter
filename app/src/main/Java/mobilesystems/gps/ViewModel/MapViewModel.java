package mobilesystems.gps.ViewModel;

import android.app.Activity;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Model.Repository.MapColorService;
import mobilesystems.gps.Model.Repository.MapCoordinateService;
import mobilesystems.gps.View.Fragments.MapView;

public class MapViewModel extends ViewModel {

    private static final String TAG = "MapViewModel";

    MapCoordinateService mapCoordinateService = new MapCoordinateService();
    MapColorService mapColorService = new MapColorService();

    MutableLiveData<Integer> index;
    MutableLiveData<List<LatLng>> coordinates;
    MutableLiveData<List<Boolean>> colorsOfSpot;
    List<LatLng> listOfLatLng = new ArrayList<>();



    public LiveData<List<LatLng>> fetchCoordinates() {
        Log.i(TAG, "fetchCoordinates called");
        if (coordinates == null) {
            coordinates = new MutableLiveData<>();
        }
        fetchCoordinatesFromModel();
        return coordinates;
    }

    private void fetchCoordinatesFromModel() {
        mapCoordinateService.fetchCoordinates(new Callback() {
            @Override
            public void onResponse(Object o) {
                if (coordinates == null) {
                    coordinates = new MutableLiveData<>();
                }
                coordinates.postValue((List<LatLng>) o);
                listOfLatLng = (List<LatLng>) o;
                Log.i(TAG, "coordinates fetched");
            }
        });
    }

    public LiveData<List<Boolean>> fetchColors() {
        Log.i(TAG, "fetchColors called");
        if (colorsOfSpot == null) {
            colorsOfSpot = new MutableLiveData<>();
        }
        fetchColorsFromModel();
        return colorsOfSpot;
    }

    private void fetchColorsFromModel() {
        mapColorService.fetchColors(new Callback() {
            @Override
            public void onResponse(Object o) {
                if (colorsOfSpot == null) {
                    colorsOfSpot = new MutableLiveData<>();
                }
                colorsOfSpot.postValue((List<Boolean>) o);
                Log.i(TAG, "colors fetched");
            }
        });
    }

    public LiveData<Integer> getNearestMarker(Location carLocation){
        Log.i(TAG, "getNearestMarker called");


        if(index == null){
            index = new MutableLiveData<>();
        }

        float min = 10000;
        float[] results = new float[3];

        Log.i(TAG, "finding nearest marker");
        for(int i = 0; i < listOfLatLng.size(); i++){
            Location.distanceBetween(listOfLatLng.get(i).latitude, listOfLatLng.get(i).longitude,
                    carLocation.getLatitude(), carLocation.getLongitude(),results);
            if(results[0] < min) {
                index.postValue(i);
                min = results[0];
            }
        }

        return index;
    }

}
