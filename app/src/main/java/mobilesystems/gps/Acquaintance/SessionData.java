package mobilesystems.gps.Acquaintance;

public class SessionData {
    private static SessionData instance;
    private IUser currentUser;

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
}
