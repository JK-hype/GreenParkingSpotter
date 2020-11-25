package mobilesystems.gps.Model.DataObjects;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users WHERE uid=:Id")
    List<User> getUserById(int Id);

    @Query("SELECT * FROM users WHERE student_mail LIKE :student_mail LIMIT 1")
    User getUserByMail(String student_mail);

    @Query("UPDATE users SET coins=:coins WHERE uid=:uid")
    void updateUser(int coins, int uid);

    @Query("UPDATE users SET parked_status=:parked_status, current_lat=:latitude, current_long=:longitude WHERE uid=:uid")
    void updateUserLocation(boolean parked_status, double latitude, double longitude, int uid);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}