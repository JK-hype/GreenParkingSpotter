package mobilesystems.gps.Acquaintance;

public interface IUser {
    int getUid();
    String getStudent_mail();
    String getPassword();
    enum getCar_type();
    String getCar_brand();
    int getCoins();
    boolean payCoins(int coins);
}
