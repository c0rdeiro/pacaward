package com.europeia.pacaward;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.core.Amplify;
import com.fidel.sdk.Fidel;
import com.fidel.sdk.LinkResult;

import java.util.HashMap;
import java.util.Map;


public class FidelSDKActivity extends AppCompatActivity {

    private static final String TAG = "SDK";


    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Fidel.programId = "dcf206fa-eeda-4e1d-b14d-0fd2f3bc7964";
        Fidel.apiKey = "pk_test_658d96a9-ea47-4aa2-bedc-acdd7bfa9057";
        Fidel.companyName = "pacaward";
        Fidel.country = Fidel.Country.UNITED_KINGDOM;
        Fidel.present(FidelSDKActivity.this);

        CognitoUserPool userPool = new CognitoUserPool(this, AWSMobileClient.getInstance().getConfiguration());
        CognitoUser currentUser = userPool.getCurrentUser();
        currentUser.getDetailsInBackground(getDetailsHandler);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Fidel.FIDEL_LINK_CARD_REQUEST_CODE) {
            if(data != null && data.hasExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD)) {
                LinkResult card = (LinkResult)data.getParcelableExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD);
                Log.i(TAG, "/cards/"+userid+"/"+card.id);
                RestOptions addCard = RestOptions.builder()
                        .addPath("/cards/"+userid+"/"+card.id)
                        .addBody("{}".getBytes())
                        .build();

                Amplify.API.post(addCard,
                        restResponse -> Log.i(TAG, restResponse.getData().asString()),
                        apiFailure -> Log.e("ApiQuickStart", apiFailure.getMessage(), apiFailure)
                );
                Log.d("d", "CARD ID = " +card.id);
            }
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("extra", "sdk");
        startActivity(intent);
        finish();
    }

    private GetDetailsHandler getDetailsHandler = new GetDetailsHandler() {

        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            Map userAtts = new HashMap();
            userAtts = cognitoUserDetails.getAttributes().getAttributes();
            userid = userAtts.get("sub").toString();


        }
        @Override
        public void onFailure(Exception exception) {
            Log.e(TAG, "onFailure: ", exception);
        }
    };

}
