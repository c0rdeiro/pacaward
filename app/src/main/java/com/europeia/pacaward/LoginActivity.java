package com.europeia.pacaward;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "Login Actitvity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("AmplifyGetStarted", "Amplify is ready for use!");
        } catch (AmplifyException configurationFailure) {
            Log.e("AmplifyGetStarted", "Failed to configure Amplify", configurationFailure);
        }
        AWSMobileClient.getInstance().initialize(this, new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails result) {
                Log.i(TAG, "onResult: entrou"+ result);
                switch (result.getUserState()){
                    case SIGNED_IN:
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        //finish();
                        break;
                    case SIGNED_OUT:
                        showSignIn();
                        break;
                    default:
                        AWSMobileClient.getInstance().signOut();
                }

            }

            @Override
            public void onError(Exception e) {
                Log.i(TAG, "onError: aws client init");
            }
        });
        //TODO: improve to show sign in after logout


    }

    private void showSignIn() {

        AWSMobileClient.getInstance().showSignIn(
                this,
                new SignInUIOptions.Builder()
                        .nextActivity(MainActivity.class)
                        .logo(R.drawable.ic_logo_transparent_small)
                        .backgroundColor(Color.LTGRAY)
                        .canCancel(true)
                        .build(),
                new Callback<UserStateDetails>() {
                    @Override
                    public void onResult(UserStateDetails result) {
                        Log.d(TAG, "onResult: " + result.getUserState());

                    }


                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "onError: ", e);
                    }
                }
        );
    }


}
