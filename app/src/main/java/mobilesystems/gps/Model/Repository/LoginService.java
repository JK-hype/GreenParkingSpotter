package mobilesystems.gps.Model.Repository;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Model.Adapters.CarAdapter;

public class LoginService {
    public void login(final Callback callback, String username, String password) {
        if(username.equals("test") && password.equals("1234")) {
            callback.onResponse(true);
        }else{
            callback.onResponse(false);
        }
    }
}
