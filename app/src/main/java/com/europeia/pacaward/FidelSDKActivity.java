package com.europeia.pacaward;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.fidel.sdk.Fidel;

public class FidelSDKActivity extends AppCompatActivity {

    private static final String TAG = "SDK";
    private WebView sdkWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fidelsdk);

        sdkWebView = findViewById(R.id.sdk);

        //add web view

        Log.i(TAG, "onCreate: SDK");
        Fidel.programId = "dcf206fa-eeda-4e1d-b14d-0fd2f3bc7964";
        Fidel.apiKey = "pk_test_658d96a9-ea47-4aa2-bedc-57";
        Fidel.companyName = "pacaward";

        WebSettings webSettings = sdkWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        //sdkWebView.loadData(Fidel.present(FidelSDKActivity.this),"text/javascript","UTF-8");
    }
}
