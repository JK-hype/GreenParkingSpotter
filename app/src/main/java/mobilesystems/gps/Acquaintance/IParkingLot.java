package mobilesystems.gps.Acquaintance;

import com.google.android.gms.maps.model.LatLng;

public interface IParkingLot {
     double getlatitude();
     double getlongitude();
     boolean getAvailability();
     void setAvailability(boolean availability);
}
