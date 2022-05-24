package be.kuleuven.bebeer.myOrders;

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
import be.kuleuven.bebeer.myOrders.MyAdapterOrder;
import be.kuleuven.bebeer.myOrders.Order;

public class OrderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapterOrder myAdapterOrder;
    ArrayList<Order> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = findViewById(R.id.orderlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapterOrder = new MyAdapterOrder(this, list);
        recyclerView.setAdapter(myAdapterOrder);

        getOrdersInRecyclerView();

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
}