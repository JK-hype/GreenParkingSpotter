package mobilesystems.gps.Model.Repository;

import android.content.Context;
import android.os.AsyncTask;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Acquaintance.Common;
import mobilesystems.gps.Model.DataObjects.User;
import mobilesystems.gps.Model.DataObjects.UserDao;

public class CreateAccountService {

    public void createAccount(final Callback callback, final String mail, final String password, final String carType, final String carBrand, final Context c) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                User user = new User();
                user.student_mail = mail;
                user.password = password;
                user.car_type = carType;
                user.car_brand = carBrand;

                UserDao userDao = Common.getInstance().getDatabase(c).userDao();
                User existingUser = userDao.findByMail(mail);
                if (existingUser == null || existingUser != user) {
                    userDao.insert(user);
                    callback.onResponse(true);
                } else {
                    callback.onResponse(false);
                }
                return null;
            }
        }.execute();
    }
}
