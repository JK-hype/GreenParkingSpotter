package mobilesystems.gps.Model.Repository;

import android.content.Context;
import android.os.AsyncTask;

import mobilesystems.gps.Acquaintance.Common;
import mobilesystems.gps.Acquaintance.IUser;
import mobilesystems.gps.Model.DataObjects.UserDao;

public class AccountService {
    public void updateUser(final IUser user, final Context c) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                UserDao userDao = Common.getInstance().getDatabase(c).userDao();
                userDao.updateUser(user.getCoins(), user.getUid());
                return null;
            }
        }.execute();
    }
}
