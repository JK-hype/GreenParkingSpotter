package mobilesystems.gps.Acquaintance;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import mobilesystems.gps.Model.DataObjects.User;
import mobilesystems.gps.Model.DataObjects.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}