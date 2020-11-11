package mobilesystems.gps.Model.Repository;

import java.sql.*;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Acquaintance.SharedData;

public class CreateAccountService {

    public void createAccount(final Callback callback, String mail, String password, String carType, String carBrand) {
        try {
            Connection db = SharedData.getDbConnection();
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("INSERT INTO users VALUES ('" + mail + "', '" + password + "', '" + carType + "', '" + carBrand + "');");
            rs.close();
            st.close();
            callback.onResponse(true);
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            callback.onResponse(false);
        }
    }
}
