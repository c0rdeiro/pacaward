package com.europeia.pacaward;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.api.rest.RestResponse;
import com.amplifyframework.core.Amplify;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

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
    public static final String CHANEL_ID = "pacaward";
    private static final String CHANEL_NAME = "pacaward";
    private static final String CHANEL_DESC = "pacaward notifications";

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
        
        initiateFirebase();


    }



//    private void handleDeviceToken(String token) {
//        RestOptions options = new RestOptions.Builder()
//                .addPath("/devices/"+userid+'/'+token)
//                .build();
//        Amplify.API.get(options,
//                response -> insertNewDevice(),
//                error -> Log.e("MyAmplifyApp", "GET failed", error));
//
//        RestOptions options = new RestOptions.Builder()
//                .addPath("/devices/"+userid+'/'+token)
//                .addBody("{}".getBytes())
//                .build();
//        Amplify.API.post(options,
//                postDevice -> Log.i(TAG, "insertNewDevice: "+ postDevice.getData().asString()),
//                error -> Log.e("POSTUSER", "GET failed", error));
//
//
//    }


    private void handleUser(RestResponse getResponse, String userId) {
        Log.i(TAG, "user id from db: "+ getResponse.getData().asString());
        if(getResponse.getData().asString().equals("[]")){
            // TODO : POST working as a get
            RestOptions options = RestOptions.builder()
                    .addPath("/users/"+ userId)
                    .addBody("{}".getBytes())
                    .build();

            Amplify.API.post(options,
                    postuser -> Log.i("POSTUSER", "POST " + postuser.getData().asString()),
                    error -> Log.e("POSTUSER", "POST failed", error)
            );

        }
    }


    private GetDetailsHandler getDetailsHandler = new GetDetailsHandler() {

        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            Map userAtts = new HashMap();
            userAtts = cognitoUserDetails.getAttributes().getAttributes();
            userid = userAtts.get("sub").toString();
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
                apiFailure -> Log.e("GETUSER", apiFailure.getMessage(), apiFailure)
        );
    }


    private void initiateFirebase() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isSuccessful()){
                            String token = task.getResult().getToken();
                            Log.i(TAG, "TOKEN: " + token);

                            // handleDeviceToken(token);

                        }else{
                            task.getException().getMessage();
                        }

                    }
                });
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

