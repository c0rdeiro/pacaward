package com.europeia.pacaward;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;


public class CardsFragment extends Fragment implements CardsAdp.OnDeleteCardListener {

    private static final String TAG = "Cards Fragment";
    private RecyclerView rvcards;
    private CardsAdp cardsAdp;
    private ArrayList<Card> cardArrayList;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton addCard;
    private TextView noCardstxt;
    private JSONArray resultCards;
    private String userid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.i(TAG, "onCreateView");
        View root = inflater.inflate(R.layout.fragment_cards, container, false);
        rvcards =(RecyclerView) root.findViewById(R.id.rv_cards);
        noCardstxt = (TextView) root.findViewById(R.id.no_cardstxt);
        cardArrayList = new ArrayList<>();
        resultCards = new JSONArray();
        addCard =(FloatingActionButton) root.findViewById(R.id.addingcardbtn);
        addCard.setOnClickListener(addCardListener);

        CognitoUserPool userPool = new CognitoUserPool(getContext(), AWSMobileClient.getInstance().getConfiguration());
        CognitoUser currentUser = userPool.getCurrentUser();
        currentUser.getDetailsInBackground(getDetailsHandler);


        return root;
    }

    private View.OnClickListener addCardListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getContext(), FidelSDKActivity.class));

        }
    };


    private void handleResponse(RestResponse restResponse) {

        try{
            JSONArray cards = new JSONArray(restResponse.getData().asString());
            final VolleyCallback callback = new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        resultCards.put(result.getJSONArray("items").getJSONObject(0));
                        if(resultCards.length() == cards.length())
                            onCards(resultCards);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(String result) {
                    Log.e("getCards",result);
                }
            };
            if(cards.length() == 0)
                setNoCardtxt();
            else
                try{
                    for(int i = 0; i < cards.length(); i++){
                        JSONObject cardObject = cards.getJSONObject(i);
                        API.call(getContext(),"cards/"+cardObject.get("idCard"), Queue.getInstance(getContext()), callback);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private void setNoCardtxt() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                noCardstxt.setText("You have no cards.\nClick the plus sign to link a card.");
            }
        });

    }


    private void onCards(JSONArray result) {
        Log.i(TAG, "oncards");
        try {
            for(int i = 0; i < result.length(); i++) {
                JSONObject jsonObject = result.getJSONObject(i);
                cardArrayList.add(new Card(jsonObject.getString("id"), jsonObject.getString("lastNumbers"), jsonObject.getString("expMonth"), jsonObject.getString("expYear"), jsonObject.getString("type")));
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        initializeRV();
    }

    private void initializeRV() {
        Log.i(TAG, "initializeRV");

        cardsAdp = new CardsAdp(getActivity(), cardArrayList, this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                linearLayoutManager = new LinearLayoutManager(getContext());
                rvcards.setLayoutManager(linearLayoutManager);
                rvcards.setAdapter(cardsAdp);

            }
        });
    }

    @Override
    public void onDeleteCardClick(final int position) {
        Log.i(TAG, "onDeleteCardClick");
        new AlertDialog.Builder(getContext())
                .setTitle("Are you sure you want to delete this card?")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedCardID = cardArrayList.get(position).getId();
                        API.delete(getContext(), String.format("cards/%s", selectedCardID), Queue.getInstance(getContext()));
                        removeItem(position);


                        Log.i(TAG, "delete: "+ "/cards/"+userid+"/"+selectedCardID );
                        RestOptions deleteCard = RestOptions.builder()
                                .addPath("/cards/"+userid+"/"+selectedCardID)
                                .build();

                        Amplify.API.delete(deleteCard,
                                response -> Log.i("MyAmplifyApp", response.getData().asString()),
                                error -> Log.e("MyAmplifyApp", "DELETE failed", error)
                        );
                        if(cardArrayList.size() == 0)
                            setNoCardtxt();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();

    }

    public void removeItem(int position){
        cardArrayList.remove(position);
        cardsAdp.notifyItemRemoved(position);

    }
    private GetDetailsHandler getDetailsHandler = new GetDetailsHandler() {

        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            Map userAtts = new HashMap();
            userAtts = cognitoUserDetails.getAttributes().getAttributes();
            userid = userAtts.get("sub").toString();
            getDbCards();


        }
        @Override
        public void onFailure(Exception exception) {
            Log.e(TAG, "onFailure: ", exception);
        }
    };

    private void getDbCards() {
        RestOptions options = new RestOptions("/cards/"+userid) ;

        Amplify.API.get(
                options,
                restResponse -> handleResponse(restResponse),
                apiFailure -> Log.e("ApiQuickStart", apiFailure.getMessage(), apiFailure)
        );
    }
}
