package mobilesystems.gps.Model.DataObjects;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import mobilesystems.gps.Acquaintance.CARTYPE;
import mobilesystems.gps.Acquaintance.IUser;
import mobilesystems.gps.Acquaintance.SessionData;
import mobilesystems.gps.Model.Repository.AccountService;

@Entity(tableName = "users")
public class User implements IUser {

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

    @ColumnInfo(name = "coins")
    public int coins;

    @Override
    public int getUid() {
        return uid;
    }

    @Override
    public String getStudent_mail() {
        return student_mail;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getCar_type() {
        return car_type;
    }

    @Override
    public String getCar_brand() {
        return car_brand;
    }

    @Override
    public int getCoins() {
        return coins;
    }

    @Override
    public boolean payCoins(int coins, Context c) {
        if (this.coins - coins < 0) {
            return false;
        }
        this.coins -= coins;
        AccountService accountService = new AccountService();
        accountService.updateUser(this, c);
        return true;
    }

    @Override
    public void gainCoins(int coins, Context c) {
        this.coins += coins;
        AccountService accountService = new AccountService();
        accountService.updateUser(this, c);
    }
}
