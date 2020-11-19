package mobilesystems.gps.Model.DataObjects;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

@Dao
public interface ParkingLotDao {
    @Query("SELECT * FROM parking_lots WHERE uid=:Id")
    List<ParkingLot> getParkingLotById(int Id);

    @Query("SELECT * FROM parking_lots")
    List<ParkingLot> getParkingLots();

    @Query("UPDATE parking_lots SET availability=:availability WHERE latitude=:latitude AND longitude=:longitude")
    void updateParkingLot(boolean availability, double latitude, double longitude);

    @Insert
    void insertAll(ParkingLot... parkingLots);
}