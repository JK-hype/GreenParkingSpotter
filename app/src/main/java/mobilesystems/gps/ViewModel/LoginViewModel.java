package mobilesystems.gps.ViewModel;

import android.content.Context;
import android.util.Pair;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Acquaintance.ICar;
import mobilesystems.gps.Model.Repository.LoginService;

public class LoginViewModel extends ViewModel {
    MutableLiveData<Pair<Boolean, String>> loginStatus;
    LoginService loginService = new LoginService();

    public LiveData<Pair<Boolean, String>> loginStatus() {
        if (loginStatus == null) {
            loginStatus = new MutableLiveData<>();
        }
        return loginStatus;
    }

    public void login(String mail, String password, Context c) {
        loginService.login(new Callback() {
            @Override
            public void onResponse(Object o) {
                loginStatus.postValue((Pair<Boolean, String>) o);
            }
        },mail,password, c);
    }
}
