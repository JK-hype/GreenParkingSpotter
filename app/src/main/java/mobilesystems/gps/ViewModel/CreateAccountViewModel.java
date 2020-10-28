package mobilesystems.gps.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import mobilesystems.gps.Acquaintance.Callback;
import mobilesystems.gps.Model.Repository.CreateAccountService;

public class CreateAccountViewModel extends ViewModel {
    MutableLiveData<Boolean> createAccountStatus;
    CreateAccountService createAccountService = new CreateAccountService();

    public LiveData<Boolean> createAccountStatus() {
        if (createAccountStatus == null) {
            createAccountStatus = new MutableLiveData<>();
        }
        return createAccountStatus;
    }

    public void createAccount(String mail, String password, String carType, String carBrand) {
        createAccountService.createAccount(new Callback() {
            @Override
            public void onResponse(Object o) {
                createAccountStatus.postValue((boolean) o);
            }
        },mail,password, carType, carBrand);
    }
}
