package com.europeia.pacaward;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;
import com.google.android.material.button.MaterialButtonToggleGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.time.chrono.MinguoChronology;
import java.util.HashMap;
import java.util.Map;

import aws.ApiClient;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "Login Actitvity";
    private EditText emailET;
    private EditText passwordET;
    private Button loginbtn;
    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = (EditText) findViewById(R.id.emailinput);
        passwordET = (EditText) findViewById(R.id.passwordinput);
        loginbtn = (Button) findViewById(R.id.loginbtn);

        connectAWS();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doInvokeAPI(emailET.getText().toString(), passwordET.getText().toString());
            }
        });
    }



    private void connectAWS() {
        Log.i(TAG, "connectAWS: started");
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d(TAG, "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();

        // Create the client
        apiClient = new ApiClientFactory()
                .credentialsProvider(null)
                .build(ApiClient.class);
    }

    public void doInvokeAPI(String mail, String pass) {
        // Create components of api request
        final String method = "GET";
        final String path = "/users";

        final String body = "";
        final byte[] content = body.getBytes(StringUtils.UTF8);

        final Map parameters = new HashMap<>();
        parameters.put("email", mail);
        parameters.put("password", pass);


        final Map headers = new HashMap<>();

        // Use components to create the api request
        ApiRequest localRequest =
                new ApiRequest(apiClient.getClass().getSimpleName())
                        .withPath(path)
                        .withHttpMethod(HttpMethodName.valueOf(method))
                        .withHeaders(headers)
                        .addHeader("Content-Type", "application/json")
                        .withParameters(parameters);

        // Only set body if it has content.
        if (body.length() > 0) {
            localRequest = localRequest
                    .addHeader("Content-Length", String.valueOf(content.length))
                    .withBody(content);
        }

        final ApiRequest request = localRequest;

        // Make network call on background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG,
                            "Invoking API w/ Request : " +
                                    request.getHttpMethod() + ":" +
                                    request.getPath());

                    final ApiResponse response = apiClient.execute(request);

                    final InputStream responseContentStream = response.getContent();

                    if (responseContentStream != null) {
                        final String responseData = IOUtils.toString(responseContentStream);
                        JSONArray responseJsonArray =new JSONArray(responseData);
                        Log.i(TAG, responseJsonArray.toString());
                        handleLogin(responseJsonArray);
                    }

                    Log.d(TAG, response.getStatusCode() + " " + response.getStatusText());

                } catch (final Exception exception) {
                    Log.e(TAG, exception.getMessage(), exception);
                    exception.printStackTrace();
                }
            }
        }).start();
    }

    private void handleLogin(JSONArray responseJsonArray) {
        if(responseJsonArray.length() == 0)
            Log.i(TAG, "handleLogin: INVALID LOGIN");
            // TODO: display invalid login user message
        else{
            startActivity((new Intent(LoginActivity.this, MainActivity.class)));
            finish();
        }
    }
}
