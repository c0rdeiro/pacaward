package com.europeia.pacaward;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.api.rest.RestResponse;
import com.amplifyframework.core.Amplify;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class TransactionsFragment extends Fragment {

    private static final String TAG = "TransactionsActivity";
    private ArrayList<Transaction> transactionsArray;
    private RecyclerView rvtransactions;
    private TextView noTransactionstxt;
    private TransactionsAdp transactionsAdp;
    private LinearLayoutManager linearLayoutManager;
    private String userid;
    private JSONArray resultTransactions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_transactions, container, false);
        rvtransactions =(RecyclerView) root.findViewById(R.id.rv_transactions);
        noTransactionstxt = (TextView) root.findViewById(R.id.no_transactionstxt);
        resultTransactions = new JSONArray();
        transactionsArray = new ArrayList<>();

        CognitoUserPool userPool = new CognitoUserPool(getContext(), AWSMobileClient.getInstance().getConfiguration());
        CognitoUser currentUser = userPool.getCurrentUser();
        currentUser.getDetailsInBackground(getDetailsHandler);

        return root;
    }

    private void handleResponse(RestResponse restResponse) {
        try{
            JSONArray transactions = new JSONArray(restResponse.getData().asString());
            final VolleyCallback callback = new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        resultTransactions.put(result.getJSONArray("items").getJSONObject(0));
                        if(resultTransactions.length() == transactions.length())
                            onTransactions(resultTransactions);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(String result) {
                    Log.e("getTransactions",result);
                }
            };
            if(transactions.length() == 0)
                setNoTransactionstxt();
            else
                try{
                    for(int i = 0; i < transactions.length(); i++){
                        JSONObject transactionObject = transactions.getJSONObject(i);
                        API.call(getContext(), "transactions/"+transactionObject.get("idTransaction"),Queue.getInstance(getContext()), callback);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private void setNoTransactionstxt() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                noTransactionstxt.setText("You have made no transactions yet.");
            }
        });
    }


    private void onTransactions(JSONArray res) {
        Log.d(TAG, "onTransactions");
        try {
            for (int i = 0; i < res.length(); i++) {
                JSONObject object = res.getJSONObject(i);
                if(!object.getJSONObject("offer").getString("cashback").equals("0"))
                    transactionsArray.add(new Transaction(object.getJSONObject("brand").getString("name"),
                            object.getJSONObject("brand").getString("logoURL"),
                            object.getJSONObject("location").getString("address"),
                            object.getString("datetime"), object.getString("amount"),
                            object.getJSONObject("offer").getString("cashback"),
                            object.getString("currency")));
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        initializeRV();
    }

    private void initializeRV() {
        transactionsAdp = new TransactionsAdp(getActivity(), transactionsArray);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                linearLayoutManager = new LinearLayoutManager(getContext());
                rvtransactions.setLayoutManager(linearLayoutManager);
                rvtransactions.setAdapter(transactionsAdp);
            }
        });

    }

    private GetDetailsHandler getDetailsHandler = new GetDetailsHandler() {

        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            Map userAtts = new HashMap();
            userAtts = cognitoUserDetails.getAttributes().getAttributes();
            userid = userAtts.get("sub").toString();
            getDbTransactions();


        }
        @Override
        public void onFailure(Exception exception) {
            Log.e(TAG, "onFailure: ", exception);
        }
    };

    private void getDbTransactions() {
        RestOptions options = new RestOptions("/transactions/"+userid) ;

        Amplify.API.get(
                options,
                restResponse -> handleResponse(restResponse),
                apiFailure -> Log.e("ApiQuickStart", apiFailure.getMessage(), apiFailure)
        );
    }

}
