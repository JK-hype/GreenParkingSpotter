package mobilesystems.gps.Model.DataObjects;

import androidx.room.Dao;
import androidx.room.Query;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

@Dao
public interface ParkingLotDao {
    @Query("SELECT * FROM parking_lots")
    List<ParkingLot> getParkingLots();

    @Query("UPDATE parking_lots SET availability=:availability WHERE coordinates=:coords")
    void updateParkingLot(boolean availability, LatLng coords);
}