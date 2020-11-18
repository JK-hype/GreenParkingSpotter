package mobilesystems.gps.Model.DataObjects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.android.gms.maps.model.LatLng;

import mobilesystems.gps.Acquaintance.IParkingLot;

@Entity(tableName = "parking_lots")
public class ParkingLot implements IParkingLot {
    @ColumnInfo(name = "coordinates")
    @NonNull
    public LatLng coordinates;

    @ColumnInfo(name = "availability")
    @NonNull
    public boolean availability;


    @Override
    public LatLng getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean getAvailability() {
        return availability;
    }

    @Override
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
