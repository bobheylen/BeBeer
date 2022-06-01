package be.kuleuven.bebeer.myOrders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Collections;

import be.kuleuven.bebeer.R;
import be.kuleuven.bebeer.activities.LoginActivity;
import be.kuleuven.bebeer.activities.RecyclerViewClickInerface;
import be.kuleuven.bebeer.myOrders.MyAdapterOrder;
import be.kuleuven.bebeer.myOrders.Order;

public class OrderActivity extends AppCompatActivity implements RecyclerViewClickInerface {

    RecyclerView recyclerView;
    MyAdapterOrder myAdapterOrder;
    ArrayList<Order> list;
    private TextView txtNoOrders;
    private Spinner spFiler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        txtNoOrders = findViewById(R.id.txtNoOrders);
        spFiler = findViewById(R.id.spFilter);

        recyclerView = findViewById(R.id.orderlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapterOrder = new MyAdapterOrder(this, list, this);
        recyclerView.setAdapter(myAdapterOrder);

        spFiler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                list.clear();
                if (spFiler.getSelectedItem().toString().equals("Last ordered, first shown")) {
                    getOrdersInRecyclerView();
                } else if (spFiler.getSelectedItem().toString().equals("First ordered, first shown")) {
                    getOrdersInRecyclerViewReverse();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getOrdersInRecyclerView() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/getOrderParametersInMap/" + LoginActivity.usernameFromLogin;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);

                                String orderID = curObject.getString("id");
                                String orderQuantity = curObject.getString("beer") + " " + curObject.getString("quantity") + "x";
                                String orderDate = "At " + curObject.getString("datum") + " between " + curObject.getString("timeslot");
                                String orderAddress = curObject.getString("address");

                                Order order = new Order(orderID, orderQuantity, orderDate, orderAddress);
                                list.add(order);
                                myAdapterOrder.notifyDataSetChanged();

                                txtNoOrders.setVisibility(View.INVISIBLE);
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
        myAdapterOrder.notifyDataSetChanged();
    }

    public void getOrdersInRecyclerViewReverse() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/getOrderParametersInMap/" + LoginActivity.usernameFromLogin;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);

                                String orderID = curObject.getString("id");
                                String orderQuantity = curObject.getString("beer") + " " + curObject.getString("quantity") + "x";
                                String orderDate = "At " + curObject.getString("datum") + " between " + curObject.getString("timeslot");
                                String orderAddress = curObject.getString("address");

                                Order order = new Order(orderID, orderQuantity, orderDate, orderAddress);
                                list.add(order);
                                Collections.reverse(list);
                                myAdapterOrder.notifyDataSetChanged();

                                txtNoOrders.setVisibility(View.INVISIBLE);
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
        myAdapterOrder.notifyDataSetChanged();
    }

    @Override
    public void updateRecyclerview() {
        list.clear();
        getOrdersInRecyclerView();
    }

}