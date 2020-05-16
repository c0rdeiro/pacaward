package com.europeia.pacaward;

import android.util.Log;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.util.IOUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import apipacaward.ApipacawardClient;

public class AWS  {
    private static ApipacawardClient apiClient;
    private static final String TAG = "AWS";
    private static JSONArray response = new JSONArray();

    public static JSONArray  doInvokeAPI(String method, String path) {
        // Create components of api request
        apiClient = new ApiClientFactory()
                .credentialsProvider(AWSMobileClient.getInstance().getCredentialsProvider())
                .build(ApipacawardClient.class);

        final Map parameters = new HashMap<>();


        // Use components to create the api request
        ApiRequest localRequest =
                new ApiRequest(apiClient.getClass().getSimpleName())
                        .withPath(path)
                        .withHttpMethod(HttpMethodName.valueOf(method))
                        .addHeader("Content-Type", "application/json")
                        .withParameters(parameters);


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
                        AWS.response = stringToJSON(responseData);
                    }

                } catch (final Exception exception) {

                    exception.printStackTrace();
                }
            }

        }).start();
        return response;
    }

    private static JSONArray stringToJSON(String responseData) throws JSONException {
        // TODO: handle post method response (JSONObject instead of JSONArray)
       return new JSONArray(responseData);
    }

}

