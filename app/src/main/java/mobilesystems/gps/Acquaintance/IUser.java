package mobilesystems.gps.Acquaintance;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public interface IUser {
    int getUid();
    String getStudent_mail();
    String getPassword();
    String getCar_type();
    String getCar_brand();
    int getCoins();
    boolean payCoins(int coins, Context c);
    void gainCoins(int coins, Context c);
    boolean getParkedStatus();
    void setParkedStatus(boolean parkedStatus, Context c);
    LatLng getLocation();
    void setLocation(Location location, Context c);
}
