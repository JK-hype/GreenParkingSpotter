package mobilesystems.gps.Acquaintance;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import mobilesystems.gps.Model.DataObjects.ParkingLot;


public class Common {

    private static Common instance;

    private Common() {}

    public static Common getInstance() {
        if (instance == null) {
            instance = new Common();
        }
        return instance;
    }

    public AppDatabase getDatabase(final Context c) {
        AppDatabase db = Room.databaseBuilder(c,
                AppDatabase.class, "database-name").build();
        return db;
    }

    public static void hideKeyboard(Context context, View view, Activity activity) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}