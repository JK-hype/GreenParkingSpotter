package mobilesystems.gps.Model.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import java.util.List;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Acquaintance.SessionData;
import mobilesystems.gps.Acquaintance.Common;
import mobilesystems.gps.Model.DataObjects.ParkingLot;
import mobilesystems.gps.Model.DataObjects.ParkingLotDao;
import mobilesystems.gps.Model.DataObjects.User;
import mobilesystems.gps.Model.DataObjects.UserDao;

public class LoginService {
    public void login(final Callback callback, final String mail, final String password, final Context c) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                verifyDatabaseInformation(c);

                UserDao userDao = Common.getInstance().getDatabase(c).userDao();
                User user = userDao.getUserByMail(mail);
                if (user == null || !user.student_mail.equals(mail)) {
                    Pair<Boolean, String> result = new Pair<>(false, "User does not exist");
                    callback.onResponse(result);
                    return null;
                }
                if (!user.password.equals(password)) {
                    Pair<Boolean, String> result = new Pair<>(false, "Wrong password. Try again");
                    callback.onResponse(result);
                    return null;
                }

                SessionData.getInstance().setCurrentUser(user);
                Pair<Boolean, String> result = new Pair<>(true, "");
                callback.onResponse(result);
                return null;
            }
        }.execute();
    }

    private void verifyDatabaseInformation(Context c) {
        ParkingLotDao parkingLotDao = Common.getInstance().getDatabase(c).parkingLotDao();
        List<ParkingLot> parkingLots = parkingLotDao.getParkingLots();
        if (parkingLots.isEmpty()) {
            parkingLotDao.insertAll(createParkingLots());
        }
    }

    private static ParkingLot[] createParkingLots() {
        return new ParkingLot[] {
                new ParkingLot(55.367575, 10.431397, true),
                new ParkingLot(55.367675, 10.431497, true),
                new ParkingLot(55.367775, 10.431597, true),
                new ParkingLot(55.367875, 10.431697, true)
        };
    }
}
