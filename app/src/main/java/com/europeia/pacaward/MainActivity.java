package com.europeia.pacaward;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Actitvity";
    private Button profileBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setSelectedItemId(R.id.nav_offers);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place, new OffersFragment()).commit();


        profileBtn = findViewById(R.id.profilebtn);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =  new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch(item.getItemId()){
                case R.id.nav_offers:
                    selectedFragment = new OffersFragment();
                    break;
                case R.id.nav_transactions:
                    selectedFragment = new TransactionsFragment();
                    break;
                case R.id.nav_cards:
                    selectedFragment = new CardsFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place, selectedFragment).commit();
            return true;
        }

    };


}