package mobilesystems.gps.Model.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Acquaintance.Common;
import mobilesystems.gps.Acquaintance.IParkingLot;
import mobilesystems.gps.Acquaintance.SessionData;
import mobilesystems.gps.Model.DataObjects.ParkingLot;
import mobilesystems.gps.Model.DataObjects.ParkingLotDao;
import mobilesystems.gps.Model.DataObjects.User;
import mobilesystems.gps.Model.DataObjects.UserDao;

public class MapCoordinateService {

    public void fetchCoordinates(final Callback callback, final Context c) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                ParkingLotDao parkingLotDao = Common.getInstance().getDatabase(c).parkingLotDao();
                List<ParkingLot> parkingLots = parkingLotDao.getParkingLots();
                callback.onResponse(parkingLots);
                return null;
            }
        }.execute();
    }

    public void updateParkingLot(final IParkingLot parkingLot, final Context c) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                ParkingLotDao parkingLotDao = Common.getInstance().getDatabase(c).parkingLotDao();
                parkingLotDao.updateParkingLot(parkingLot.getAvailability(), parkingLot.getlatitude(), parkingLot.getlongitude());
                return null;
            }
        }.execute();
    }
}
