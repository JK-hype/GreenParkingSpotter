package mobilesystems.gps.Acquaintance;

import android.content.Context;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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

    public static void hideKeyboard(Context context, View view, Activity activity) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}