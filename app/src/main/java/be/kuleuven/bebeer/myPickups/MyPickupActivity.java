package be.kuleuven.bebeer.myPickups;

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
import be.kuleuven.bebeer.activities.LoginActivity;
import be.kuleuven.bebeer.activities.RecyclerViewClickInerface;
import be.kuleuven.bebeer.myPickups.MyAdapterMyPickup;
import be.kuleuven.bebeer.myPickups.MyPickup;

public class MyPickupActivity extends AppCompatActivity implements RecyclerViewClickInerface {

    RecyclerView recyclerView;
    MyAdapterMyPickup myAdapterMyPickup;
    ArrayList<MyPickup> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pickups);

        recyclerView = findViewById(R.id.mypickuplist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapterMyPickup = new MyAdapterMyPickup(this, list, this);
        recyclerView.setAdapter(myAdapterMyPickup);

        getMyPickupsInRecyclerView();
    }

    public void getMyPickupsInRecyclerView()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/getAllParamFromMypickup/" + LoginActivity.usernameFromLogin;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);

                                String id = curObject.getString("id");
                                String usernameBakOwner = curObject.getString("UsernameBakOwner");
                                String firstname = curObject.getString("firstname");
                                String name = curObject.getString("name");
                                String pickupDate = curObject.getString("pickupDate");
                                String pickupTime = curObject.getString("pickupTime");
                                String pickupAddress = curObject.getString("pickupAddress");
                                String quantityBak = curObject.getString("quantityBak");
                                String phonenumber = curObject.getString("phonenumber");
                                MyPickup myPickup = new MyPickup(id, usernameBakOwner, firstname, name, pickupDate, pickupTime, pickupAddress, quantityBak, phonenumber);
                                list.add(myPickup);
                                myAdapterMyPickup.notifyDataSetChanged();
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
    }

    @Override
    public void updateRecyclerview() {
        list.clear();
        getMyPickupsInRecyclerView();
    }
}