package mobilesystems.gps.Model.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import java.util.List;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Acquaintance.Common;
import mobilesystems.gps.Acquaintance.IUser;
import mobilesystems.gps.Acquaintance.SessionData;
import mobilesystems.gps.Model.DataObjects.ParkingLot;
import mobilesystems.gps.Model.DataObjects.ParkingLotDao;
import mobilesystems.gps.Model.DataObjects.User;
import mobilesystems.gps.Model.DataObjects.UserDao;

public class UserService {
    public void updateUser(final IUser user, final Context c) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                UserDao userDao = Common.getInstance().getDatabase(c).userDao();
                userDao.updateUserLocation(user.getParkedStatus(), user.getLocation().latitude, user.getLocation().longitude, user.getUid());
                return null;
            }
        }.execute();
    }
}
