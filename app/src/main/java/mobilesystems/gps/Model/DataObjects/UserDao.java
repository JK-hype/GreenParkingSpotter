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

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}