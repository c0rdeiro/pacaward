package com.europeia.pacaward;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Actitvity";
    private Button profileBtn;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        bottomNavigationView.setSelectedItemId(R.id.nav_offers);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place, new OffersFragment()).commit();

        checkIntent();

        profileBtn = findViewById(R.id.profilebtn);
    }

    private void checkIntent(){
        if(getIntent().getStringExtra("extra") != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place, new CardsFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_cards);
        }
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