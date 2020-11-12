package mobilesystems.gps.Model.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Pair;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Acquaintance.SharedData;
import mobilesystems.gps.Model.Adapters.CarAdapter;
import mobilesystems.gps.Model.DataObjects.User;
import mobilesystems.gps.Model.DataObjects.UserDao;

public class LoginService {
    public void login(final Callback callback, final String mail, final String password, final Context c) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                User user = new User();
                user.student_mail = mail;
                user.password = password;

                UserDao userDao = SharedData.getInstance().getDatabase(c).userDao();
                User existingUser = userDao.findByMail(mail);
                if (existingUser == null || !existingUser.student_mail.equals(mail)) {
                    Pair<Boolean, String> result = new Pair<>(false, "User does not exist");
                    callback.onResponse(result);
                    return null;
                }
                if (!existingUser.password.equals(password)) {
                    Pair<Boolean, String> result = new Pair<>(false, "Wrong password. Try again");
                    callback.onResponse(result);
                    return null;
                }

                Pair<Boolean, String> result = new Pair<>(true, "");
                callback.onResponse(result);
                return null;
            }
        }.execute();
    }
}
