package com.europeia.pacaward;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OfferDetailedActivity extends AppCompatActivity {

    private Offer offer;
    private ImageView img;
    private TextView title;
    private TextView brand;
    private TextView location;
    private Button close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_item_detailed);

        img = findViewById(R.id.detailedimg);
        title = findViewById(R.id.detailedtitle);
        brand = findViewById(R.id.detailedbrand);
        location = findViewById(R.id.detailedlocation);
        close = findViewById(R.id.closeoffer);
        close.setOnClickListener(closelistener);
        offer = (Offer) getIntent().getSerializableExtra("offer");

        Glide.with(this).asBitmap().load(offer.getImageUrl()).into(img);
        title.setText(offer.getTitle());
        brand.setText(offer.getBrandName());

        getLocation(offer.getId());
    }

    private void getLocation(String id) {
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                onLocations(result);
            }
            @Override
            public void onError(String result) {
                Log.e("getLocations",result);
            }
        };
        String endpoint = String.format("offers/%s/locations", id);
        API.call(endpoint,0, Queue.getInstance(this), callback);
    }

    private void onLocations(JSONObject result) {
        try {
            JSONObject obj = result.getJSONArray("items").getJSONObject(0);

            String locationS = String.format("%s %s \n %s", obj.get("address"), obj.get("postcode"), obj.get("city"));
            location.setText(locationS);

        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }


    private View.OnClickListener closelistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();

        }
    };
}
