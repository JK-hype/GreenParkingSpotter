package mobilesystems.gps.View.Activities;

import android.location.Location;

public interface NavigationDrawerMenu {
    void setDrawerLocked(boolean lock);

    void setToolbarVisibility(boolean visible);

    void setItemPark();

    void setLocation(Location location);
}
