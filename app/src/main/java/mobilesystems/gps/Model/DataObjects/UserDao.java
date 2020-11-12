package mobilesystems.gps.Model.DataObjects;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM users WHERE student_mail LIKE :student_mail LIMIT 1")
    User findByMail(String student_mail);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}