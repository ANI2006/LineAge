package com.app.lineage6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;


import com.app.lineage6.databinding.ActivityMainBinding;
import com.app.lineage6.db.OnClickItemInterface;
import com.app.lineage6.ui.SignInDialog;
import com.app.lineage6.ui.user.UserAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class  MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean showSignInDialog = preferences.getBoolean("show_signin_dialog", true);

        if (showSignInDialog) {
            showSignInDialog();
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_user, R.id.navigation_relations, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }
    private void showSignInDialog() {
        SignInDialog signInDialog = new SignInDialog();
        signInDialog.show(getSupportFragmentManager(), "signin_dialog");
    }

}