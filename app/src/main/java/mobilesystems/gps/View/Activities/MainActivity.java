package mobilesystems.gps.View.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import mobilesystems.gps.R;
import mobilesystems.gps.View.Fragments.LoginView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        if (savedInstanceState == null) {
            LoginView loginView = new LoginView();
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.mainFragment, loginView);
            fragmentTransaction.commit();
        }
    }

    private void checkPermission() {
        if (this != null) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }
        }
    }
}
