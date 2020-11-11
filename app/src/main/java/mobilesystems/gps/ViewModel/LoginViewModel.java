package mobilesystems.gps.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Acquaintance.ICar;
import mobilesystems.gps.Model.Repository.LoginService;

public class LoginViewModel extends ViewModel {
    MutableLiveData<Boolean> loginStatus;
    LoginService loginService = new LoginService();

    public LiveData<Boolean> loginStatus() {
        if (loginStatus == null) {
            loginStatus = new MutableLiveData<>();
        }
        return loginStatus;
    }

    public void login(String username, String password) {
        loginService.login(new Callback() {
            @Override
            public void onResponse(Object o) {
                loginStatus.postValue((boolean) o);
            }
        },username,password);
    }
}
