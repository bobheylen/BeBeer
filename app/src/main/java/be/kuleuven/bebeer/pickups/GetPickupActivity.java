package be.kuleuven.bebeer.pickups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import be.kuleuven.bebeer.R;
import be.kuleuven.bebeer.activities.RecyclerViewClickInerface;

public class GetPickupActivity extends AppCompatActivity implements RecyclerViewClickInerface {

    RecyclerView recyclerView;
    MyAdapterPickup myAdapterPickup;
    ArrayList<Pickup> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pickup);


        recyclerView = findViewById(R.id.pickuplist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));

        list = new ArrayList<>();
        myAdapterPickup = new MyAdapterPickup(this, list, this);
        recyclerView.setAdapter(myAdapterPickup);

        getPickupsInRecyclerView();

    }

    public void getPickupsInRecyclerView() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/getAllParamFromPickup";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);

                                String pickupID = curObject.getString("id");
                                System.out.println(pickupID);
                                String usernameGive = curObject.getString("usernameGive");
                                String address = curObject.getString("address");
                                String quantityBak = curObject.getString("quantityBak");
                                String pickupDate = curObject.getString("pickupDate");
                                String pickupTime = curObject.getString("pickupTime");
                                Pickup pickup = new Pickup(pickupID, usernameGive, address, quantityBak, pickupDate, pickupTime);
                                list.add(pickup);
                                myAdapterPickup.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            Log.e("Database", e.getMessage(), e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(request);
        myAdapterPickup.notifyDataSetChanged();
    }

    @Override
    public void updateRecyclerview() {
        list.clear();
        getPickupsInRecyclerView();
    }
}