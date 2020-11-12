package mobilesystems.gps.Model.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Acquaintance.SessionData;
import mobilesystems.gps.Acquaintance.Common;
import mobilesystems.gps.Model.DataObjects.User;
import mobilesystems.gps.Model.DataObjects.UserDao;

public class LoginService {
    public void login(final Callback callback, final String mail, final String password, final Context c) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                UserDao userDao = Common.getInstance().getDatabase(c).userDao();
                User user = userDao.findByMail(mail);
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
}
