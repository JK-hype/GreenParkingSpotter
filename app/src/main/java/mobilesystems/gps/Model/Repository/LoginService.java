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
                new ParkingLot(55.367875, 10.431697, true),
                new ParkingLot(55.368092, 10.431376, true),
                new ParkingLot(55.368094, 10.431411,true),
                new ParkingLot(55.368095, 10.431450,true),
                new ParkingLot(55.368097, 10.431489,true),
                new ParkingLot(55.368099, 10.431527,true),
                new ParkingLot(55.368100, 10.431563,true),
                new ParkingLot(55.368100, 10.431603,true),
                new ParkingLot(55.368104, 10.431640,true),
                new ParkingLot(55.368105, 10.431679,true),
                new ParkingLot(55.368107, 10.431716,true),
                new ParkingLot(55.368110, 10.431754,true),
                new ParkingLot(55.368112, 10.431794,true),
                new ParkingLot(55.368114, 10.431829,true),
                new ParkingLot(55.368115, 10.431869,true),
                new ParkingLot(55.368117, 10.431906,true),
                new ParkingLot(55.368118, 10.431942,true),
                new ParkingLot(55.368119, 10.431979,true),
                new ParkingLot(55.368121, 10.432017,true),
                new ParkingLot(55.368122, 10.432056,true),
                new ParkingLot(55.368125, 10.432092,true),
                new ParkingLot(55.368126, 10.432130,true),
                new ParkingLot(55.368128, 10.432165,true),
                new ParkingLot(55.368129, 10.432205,true),
                new ParkingLot(55.368131, 10.432243,true),
                new ParkingLot(55.368132, 10.432284,true),
                new ParkingLot(55.368135, 10.432323,true),
                new ParkingLot(55.368136, 10.432356,true),
                new ParkingLot(55.368136, 10.432396,true),
                new ParkingLot(55.368138, 10.432434,true),
                new ParkingLot(55.368139, 10.432469,true),
                new ParkingLot(55.368141, 10.432506,true),
                new ParkingLot(55.368145, 10.432544,true)
        };
    }
}
