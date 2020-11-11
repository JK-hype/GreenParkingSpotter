package mobilesystems.gps.ViewModel;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Model.Repository.MapService;

public class MapViewModel extends ViewModel{

    Location location;
    FusedLocationProviderClient fusedLocationProviderClient;
    MapService mapService;
    Location carLocation;

    MutableLiveData<Integer> index;
    MutableLiveData<List<LatLng>> coordinates;
    List<LatLng> listOfLatLng = new ArrayList<>();

    public LiveData<List<LatLng>> fetchCoordinates(){
        if(coordinates == null){
            coordinates = new MutableLiveData<>();
        }
        fetchCoordinatesFromModel();
        return coordinates;
    }

    private void fetchCoordinatesFromModel(){
        mapService.fetchCoordinates(new Callback() {
            @Override
            public void onResponse(Object o) {
                if(coordinates == null){
                    coordinates = new MutableLiveData<>();
                }
                coordinates.postValue((List<LatLng>) o);
                listOfLatLng = (List<LatLng>) o;
            }
        });
    }

    public void fetchCarLocation() {

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    carLocation = location;
                    getNearestMarker();
                }
            }
        });
    }

    public LiveData<Integer> getNearestMarker(){
        if(index == null){
            index = new MutableLiveData<>();
        }

        float min = 10000;
        float[] results = new float[3];
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
