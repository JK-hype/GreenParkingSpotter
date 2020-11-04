package mobilesystems.gps.Model.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Acquaintance.SharedData;
import mobilesystems.gps.Model.Adapters.CarAdapter;

public class LoginService {
    public void login(final Callback callback, String mail, String password) {
        try {
            Connection db = SharedData.getDbConnection();
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE mail='" + mail + "' AND password='" + password + "';");
            if (rs.getString(1) != null) {
                callback.onResponse(true);
            } else {
                callback.onResponse(false);
            }
            rs.close();
            st.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            callback.onResponse(false);
        }
    }
}
