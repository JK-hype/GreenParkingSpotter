package mobilesystems.gps.Acquaintance;

import android.content.Context;

public interface IUser {
    int getUid();
    String getStudent_mail();
    String getPassword();
    String getCar_type();
    String getCar_brand();
    int getCoins();
    boolean payCoins(int coins, Context c);
    void gainCoins(int coins, Context c);
}
