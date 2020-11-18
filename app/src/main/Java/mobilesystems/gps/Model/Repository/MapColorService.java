package mobilesystems.gps.Model.Repository;

import java.util.ArrayList;
import java.util.List;

import mobilesystems.gps.Acquaintance.Callback;

public class MapColorService {
    List<Boolean> colorsOfSpot = new ArrayList<>();

    public void fetchColors(final Callback callback){
        colorsOfSpot.add(true);
        colorsOfSpot.add(false);
        colorsOfSpot.add(false);
        colorsOfSpot.add(false);

        callback.onResponse(colorsOfSpot);
    }
}
