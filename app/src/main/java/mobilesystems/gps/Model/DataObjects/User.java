package mobilesystems.gps.Model.DataObjects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User{
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "student_mail")
    @NonNull
    public String student_mail;

    @ColumnInfo(name = "password")
    @NonNull
    public String password;

    @ColumnInfo(name = "car_type")
    public String car_type;

    @ColumnInfo(name = "car_brand")
    public String car_brand;
}
