package mobilesystems.gps.Acquaintance;

import android.location.Location;

public class SessionData {
    private static SessionData instance;
    private IUser currentUser;
    private Location location;

    private SessionData() {}

    public static SessionData getInstance() {
        if (instance == null) {
            instance = new SessionData();
        }
        return instance;
    }

    public IUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(IUser user) {
        currentUser = user;
    }

    public Location getLocation() { return location; }

    public void setLocation(Location location) {
        this.location = location;
    }
}
