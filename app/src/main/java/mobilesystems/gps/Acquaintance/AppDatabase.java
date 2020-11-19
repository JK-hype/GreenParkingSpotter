package mobilesystems.gps.Acquaintance;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import mobilesystems.gps.Model.DataObjects.ParkingLot;
import mobilesystems.gps.Model.DataObjects.ParkingLotDao;
import mobilesystems.gps.Model.DataObjects.User;
import mobilesystems.gps.Model.DataObjects.UserDao;

@Database(entities = {User.class, ParkingLot.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ParkingLotDao parkingLotDao();
}