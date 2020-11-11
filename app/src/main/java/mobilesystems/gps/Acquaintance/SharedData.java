package mobilesystems.gps.Acquaintance;

import java.sql.Connection;
import java.sql.DriverManager;

public class SharedData {
    private static String db_url = "jdbc:postgresql://hattie.db.elephantsql.com:5432/hgtgbzti";
    private static String db_user = "hgtgbzti";
    private static String db_pw = "WC5-4JEsrncWqCWa16P8uUOwdPlx5zJN";
    public static Connection getDbConnection() {
        Connection db = null;
        try {
            Class.forName("org.postgresql.Driver");
            db = DriverManager.getConnection(db_url, db_user, db_pw);
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return db;
    }
}