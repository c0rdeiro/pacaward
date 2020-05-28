package com.europeia.pacaward;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amplifyframework.api.rest.RestOperation;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.api.rest.RestResponse;
import com.amplifyframework.core.Amplify;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Actitvity";
    private ImageButton profileBtn;
    private BottomNavigationView bottomNavigationView;
    private RelativeLayout toolbar;
    private boolean logout = false;
    private CardView logoutcard;
    private TextView emailtxt;
    private Button logoutbtn;
    private CognitoUser currentUser;
    private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileBtn = findViewById(R.id.profilebtn);
        toolbar = findViewById(R.id.toolbar);
        logoutcard = findViewById(R.id.logoutcard);
        emailtxt = findViewById(R.id.profilemail);
        logoutbtn = findViewById(R.id.logoutbutton);
        bottomNavigationView = findViewById(R.id.navbar);

        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.nav_offers);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place, new OffersFragment()).commit();
        checkIntent();

        profileBtn.setOnClickListener(profilebtnListener);
        logoutbtn.setOnClickListener(logoutbtnListener);

        CognitoUserPool userPool = new CognitoUserPool(this, AWSMobileClient.getInstance().getConfiguration());
        currentUser = userPool.getCurrentUser();
        currentUser.getDetailsInBackground(getDetailsHandler);


    }


    private void handleUser(RestResponse getResponse, String userId) {
        Log.i(TAG, "user id from db: "+ getResponse.getData().asString());
        if(getResponse.getData().asString().equals("[]")){
            // TODO : POST working as a get
            RestOptions options = RestOptions.builder()
                    .addPath("/users/"+ userId)
                    .addBody("{}".getBytes())
                    .build();

            Amplify.API.post(options,
                    response -> Log.i("MyAmplifyApp", "POST " + response.getData().asString()),
                    error -> Log.e("MyAmplifyApp", "POST failed", error)
            );

        }
    }


    private GetDetailsHandler getDetailsHandler = new GetDetailsHandler() {

        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            Map userAtts = new HashMap();
            userAtts = cognitoUserDetails.getAttributes().getAttributes();
            userid = userAtts.get("sub").toString();
            Log.i(TAG, "ID: " + userid);
            emailtxt.setText(userAtts.get("email").toString());
            getUserFromDB();

        }
        @Override
        public void onFailure(Exception exception) {
            Log.e(TAG, "onFailure: ", exception);
        }
    };

    private void getUserFromDB() {
        RestOptions options = RestOptions.builder()
                .addPath("/users/"+ userid)
                .build();

        Amplify.API.get("apipacaward",
                options,
                getResponse -> handleUser(getResponse, userid),
                apiFailure -> Log.e("ApiQuickStart", apiFailure.getMessage(), apiFailure)
        );
    }


    private View.OnClickListener logoutbtnListener = new View.OnClickListener(){
        public void onClick(View view){
            currentUser.signOut();
            AWSMobileClient.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    };

    private View.OnClickListener profilebtnListener = new View.OnClickListener() {

        public void onClick(View v) {

            if(logout){
                v.setBackgroundResource(R.drawable.profileicon);
                toolbar.setBackgroundResource(R.color.colorPrimary);
                logoutcard.setVisibility(View.INVISIBLE);
                emailtxt.setVisibility(View.INVISIBLE);

            }else if(!logout){
                v.setBackgroundResource(R.drawable.ic_profileiconlogout);
                toolbar.setBackgroundResource(R.color.colorAccent);
                logoutcard.setVisibility(View.VISIBLE);
                emailtxt.setVisibility(View.VISIBLE);
            }
            logout = !logout;
        }
    };
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

