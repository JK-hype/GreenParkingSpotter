package mobilesystems.gps.Model.DataObjects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import mobilesystems.gps.Acquaintance.IParkingLot;

@Entity(tableName = "parking_lots")
public class ParkingLot implements IParkingLot {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "latitude")
    @NonNull
    public double latitude;

    @ColumnInfo(name = "longitude")
    @NonNull
    public double longitude;

    @ColumnInfo(name = "availability")
    @NonNull
    public boolean availability;

    @Override
    public double getlatitude() {
        return latitude;
    }

    @Override
    public double getlongitude() {
        return longitude;
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
