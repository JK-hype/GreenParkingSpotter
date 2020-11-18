package mobilesystems.gps.Acquaintance;

import com.google.android.gms.maps.model.LatLng;

public interface IParkingLot {
     LatLng getCoordinates();
     boolean getAvailability();
     void setAvailability(boolean availability);
}
