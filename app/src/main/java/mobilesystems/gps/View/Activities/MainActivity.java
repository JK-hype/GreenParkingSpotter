package mobilesystems.gps.View.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.view.GravityCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import mobilesystems.gps.R;
import mobilesystems.gps.View.Fragments.AboutView;
import mobilesystems.gps.View.Fragments.AccountView;
import mobilesystems.gps.View.Fragments.LoginView;
import mobilesystems.gps.View.Fragments.ParkingView;
import mobilesystems.gps.View.Fragments.PerksView;
import mobilesystems.gps.ViewModel.LoginViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NavigationDrawerMenu {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    LoginViewModel loginVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        loginVM = new ViewModelProvider(this).get(LoginViewModel.class);

        drawerLayout = findViewById(R.id.mainFragment);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar_menu);

        setSupportActionBar(toolbar);

        //LoginView
        // Navigation Drawer Menu
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigationDrawerOpen, R.string.navigationDrawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            LoginView loginView = new LoginView();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_fragments, loginView); // <-- THIS IS WORKING! I used container_fragments instead of the mainFragment
            fragmentTransaction.commit();
        }
    }

    private void checkPermissions() {
        if (this != null) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        FragmentTransaction fragmentTransaction;

        switch (menuItem.getItemId()) {
            case R.id.item_park:
                ParkingView parkingView = new ParkingView();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_fragments, parkingView);
                fragmentTransaction.addToBackStack("ParkingView");
                fragmentTransaction.commit();
                break;
            case R.id.item_map:
                LoginView loginView = new LoginView();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_fragments, loginView);
                fragmentTransaction.addToBackStack("LoginView");
                fragmentTransaction.commit();
                break;
            case R.id.item_perks:
                PerksView perksView = new PerksView();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_fragments, perksView);
                fragmentTransaction.addToBackStack("LoginView");
                fragmentTransaction.commit();
                break;
            case R.id.item_account:
                AccountView accountView = new AccountView();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_fragments, accountView);
                fragmentTransaction.addToBackStack("LoginView");
                fragmentTransaction.commit();
                break;
            case R.id.item_about:
                AboutView aboutView = new AboutView();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_fragments, aboutView);
                fragmentTransaction.addToBackStack("AboutView");
                fragmentTransaction.commit();
                break;
            case R.id.item_logout:
                loginVM.logout();

                loginView = new LoginView();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_fragments, loginView);
                fragmentTransaction.addToBackStack("LoginView");
                fragmentTransaction.commit();
                break;
            default:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setDrawerLocked(boolean enabled) {
        if (enabled) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void setToolbarVisibility(boolean visible) {
        if (visible) {
            findViewById(R.id.toolbar_menu).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.toolbar_menu).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setItemPark() {
        navigationView.setCheckedItem(R.id.item_park);
    }
}
