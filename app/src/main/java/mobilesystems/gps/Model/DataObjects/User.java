package mobilesystems.gps.Model.DataObjects;

import android.content.Context;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import mobilesystems.gps.Acquaintance.CARTYPE;
import mobilesystems.gps.Acquaintance.IUser;
import mobilesystems.gps.Acquaintance.SessionData;
import mobilesystems.gps.Model.Repository.AccountService;
import mobilesystems.gps.Model.Repository.UserService;

@Entity(tableName = "users", indices = {@Index(value = "student_mail", unique = true)})
public class User implements IUser {

    @PrimaryKey
    public Integer uid;

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

    @ColumnInfo(name = "parked_status")
    public boolean parked_status;

    @ColumnInfo(name = "current_lat")
    public double current_lat;

    @ColumnInfo(name = "current_long")
    public double current_long;

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

    @Override
    public boolean getParkedStatus() {
        return parked_status;
    }

    @Override
    public void setParkedStatus(boolean parkedStatus, Context c) {
        parked_status = parkedStatus;
        UserService userService = new UserService();
        userService.updateUser(this, c);
    }

    @Override
    public LatLng getLocation() {
        return new LatLng(current_lat, current_long);
    }

    @Override
    public void setLocation(Location location, Context c) {
        current_lat = location.getLatitude();
        current_long = location.getLongitude();
        UserService userService = new UserService();
        userService.updateUser(this, c);
    }
}
