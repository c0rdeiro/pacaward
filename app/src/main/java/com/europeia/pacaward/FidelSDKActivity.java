package com.europeia.pacaward;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fidel.sdk.Fidel;
import com.fidel.sdk.LinkResult;

public class FidelSDKActivity extends AppCompatActivity {

    private static final String TAG = "SDK";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate: SDK");
        Fidel.programId = "dcf206fa-eeda-4e1d-b14d-0fd2f3bc7964";
        Fidel.apiKey = "pk_test_658d96a9-ea47-4aa2-bedc-acdd7bfa9057";
        Fidel.companyName = "pacaward";
        Fidel.country = Fidel.Country.UNITED_KINGDOM;

        Fidel.present(FidelSDKActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Fidel.FIDEL_LINK_CARD_REQUEST_CODE) {
            if(data != null && data.hasExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD)) {
                LinkResult card = (LinkResult)data.getParcelableExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD);
                Log.d("d", "CARD ID = " + card.id);
            }
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("extra", "sdk");
        startActivity(intent);
        finish();
    }


}
