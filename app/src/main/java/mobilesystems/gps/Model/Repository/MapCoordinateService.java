package mobilesystems.gps.Model.Repository;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import mobilesystems.gps.Acquaintance.Callback;

public class MapCoordinateService {
    LatLng latLng1 = new LatLng(55.367575, 10.431397);
    LatLng latLng2 = new LatLng(55.367675, 10.431497);
    LatLng latLng3 = new LatLng(55.367775, 10.431597);
    LatLng latLng4 = new LatLng(55.367875, 10.431697);
    List<LatLng> latLngs = new ArrayList<>();


    public void fetchCoordinates(final Callback callback) {
        latLngs.add(latLng1);
        latLngs.add(latLng2);
        latLngs.add(latLng3);
        latLngs.add(latLng4);

        callback.onResponse(latLngs);
    }
}
