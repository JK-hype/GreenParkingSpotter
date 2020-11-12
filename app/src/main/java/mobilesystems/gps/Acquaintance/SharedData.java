package mobilesystems.gps.Acquaintance;

import android.content.Context;

import androidx.room.Room;

import java.sql.Connection;
import java.sql.DriverManager;

public class SharedData {

    private static SharedData instance;

    private SharedData() {}

    public static SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }

    public AppDatabase getDatabase(Context c) {
        AppDatabase db = Room.databaseBuilder(c,
                AppDatabase.class, "database-name").build();
        return db;
    }
}