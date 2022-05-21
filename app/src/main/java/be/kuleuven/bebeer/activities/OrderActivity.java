package be.kuleuven.bebeer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.kuleuven.bebeer.R;
import be.kuleuven.bebeer.activities.LoginActivity;

public class OrderActivity extends AppCompatActivity {

    private ArrayList<Integer> ids = new ArrayList<>();
    private Map<Integer, String> beerMap = new HashMap<>();
    private Map<Integer, String> quantityMap = new HashMap<>();
    private Map<Integer, String> datumMap = new HashMap<>();
    private Map<Integer, String> timeslotMap = new HashMap<>();
    private Map<Integer, String> addressMap = new HashMap<>();
    private TextView orderID1, orderID2, orderID3, orderID4, orderID5;
    private TextView txtIDfixt1, txtIDfixt2, txtIDfixt3, txtIDfixt4, txtIDfixt5;
    private TextView beerANDqty1, beerANDqty2, beerANDqty3, beerANDqty4, beerANDqty5;
    private TextView datetime1, datetime2, datetime3, datetime4, datetime5;
    private TextView address1, address2, address3, address4, address5;
    private Button btnCancelOrder1, btnCancelOrder2, btnCancelOrder3, btnCancelOrder4, btnCancelOrder5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderID1 = findViewById(R.id.txtID1);
        orderID2 = findViewById(R.id.txtID2);
        orderID3 = findViewById(R.id.txtID3);
        orderID4 = findViewById(R.id.txtID4);
        orderID5 = findViewById(R.id.txtID5);
        txtIDfixt1 = findViewById(R.id.txtIDfixt1);
        txtIDfixt2 = findViewById(R.id.txtIDfixt2);
        txtIDfixt3 = findViewById(R.id.txtIDfixt3);
        txtIDfixt4 = findViewById(R.id.txtIDfixt4);
        txtIDfixt5 = findViewById(R.id.txtIDfixt5);
        beerANDqty1 = findViewById(R.id.txtOrderBeer1);
        beerANDqty2 = findViewById(R.id.txtOrderBeer2);
        beerANDqty3 = findViewById(R.id.txtOrderBeer3);
        beerANDqty4 = findViewById(R.id.txtOrderBeer4);
        beerANDqty5 = findViewById(R.id.txtOrderBeer5);
        datetime1 = findViewById(R.id.txtOrderDatetime1);
        datetime2 = findViewById(R.id.txtOrderDatetime2);
        datetime3 = findViewById(R.id.txtOrderDatetime3);
        datetime4 = findViewById(R.id.txtOrderDatetime4);
        datetime5 = findViewById(R.id.txtOrderDatetime5);
        address1 = findViewById(R.id.txtOrderAddress1);
        address2 = findViewById(R.id.txtOrderAddress2);
        address3 = findViewById(R.id.txtOrderAddress3);
        address4 = findViewById(R.id.txtOrderAddress4);
        address5 = findViewById(R.id.txtOrderAddress5);
        btnCancelOrder1 = findViewById(R.id.btnCancelOrder1);
        btnCancelOrder2 = findViewById(R.id.btnCancelOrder2);
        btnCancelOrder3 = findViewById(R.id.btnCancelOrder3);
        btnCancelOrder4 = findViewById(R.id.btnCancelOrder4);
        btnCancelOrder5 = findViewById(R.id.btnCancelOrder5);

        getOrderParametersInMap();
        // *** 0,5 seconden wachten voor volgende functie wordt uitgevoerd ***
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 0,5 seconds
                setOrdersInTextViews();
                System.out.println("********** 0,5 sec gewacht **********");
                System.out.println(ids);
                System.out.println(beerMap);
                System.out.println(quantityMap);
                System.out.println(datumMap);
                System.out.println(timeslotMap);
                System.out.println(addressMap);
                System.out.println("********** 0,5 sec gewacht **********");
            }
        }, 500);
        // *** 0,5 seconden wachten voor volgende functie wordt uitgevoerd ***

        btnCancelOrder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ids.clear();
                beerMap.clear();
                quantityMap.clear();
                datumMap.clear();
                timeslotMap.clear();
                addressMap.clear();
                deleteOrder(1);
                getOrderParametersInMap();

                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 0,5 seconds
                        setOrdersInTextViews();
                    }
                }, 500);
            }
        });

        btnCancelOrder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ids.clear();
                beerMap.clear();
                quantityMap.clear();
                datumMap.clear();
                timeslotMap.clear();
                addressMap.clear();
                deleteOrder(2);
                getOrderParametersInMap();

                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 0,5 seconds
                        setOrdersInTextViews();
                    }
                }, 500);
            }
        });

        btnCancelOrder3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ids.clear();
                beerMap.clear();
                quantityMap.clear();
                datumMap.clear();
                timeslotMap.clear();
                addressMap.clear();
                deleteOrder(3);
                getOrderParametersInMap();

                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 0,5 seconds
                        setOrdersInTextViews();
                    }
                }, 500);
            }
        });

        btnCancelOrder4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ids.clear();
                beerMap.clear();
                quantityMap.clear();
                datumMap.clear();
                timeslotMap.clear();
                addressMap.clear();
                deleteOrder(4);
                getOrderParametersInMap();

                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 0,5 seconds
                        setOrdersInTextViews();
                    }
                }, 500);
            }
        });

        btnCancelOrder5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ids.clear();
                beerMap.clear();
                quantityMap.clear();
                datumMap.clear();
                timeslotMap.clear();
                addressMap.clear();
                deleteOrder(5);
                getOrderParametersInMap();

                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 0,5 seconds
                        setOrdersInTextViews();
                    }
                }, 500);
            }
        });
    }

    public void getOrderParametersInMap() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/getOrderParametersInMap/" + LoginActivity.usernameFromLogin;
        JsonArrayRequest loginRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);
                                int id = Integer.parseInt(curObject.getString("id"));
                                ids.add(id);
                                beerMap.put(id, curObject.getString("beer"));
                                quantityMap.put(id, curObject.getString("quantity"));
                                datumMap.put(id, curObject.getString("datum"));
                                timeslotMap.put(id, curObject.getString("timeslot"));
                                addressMap.put(id, curObject.getString("address"));
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
        requestQueue.add(loginRequest);

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 0,5 seconds
                setOrdersInTextViews();
            }
        }, 500);
    }

    public void setOrdersInTextViews() {
        if (ids.size() == 0) {
            orderID1.setVisibility(View.GONE);
            orderID2.setVisibility(View.GONE);
            orderID3.setVisibility(View.GONE);
            orderID4.setVisibility(View.GONE);
            orderID5.setVisibility(View.GONE);
            txtIDfixt1.setVisibility(View.GONE);
            txtIDfixt2.setVisibility(View.GONE);
            txtIDfixt3.setVisibility(View.GONE);
            txtIDfixt4.setVisibility(View.GONE);
            txtIDfixt5.setVisibility(View.GONE);
            beerANDqty1.setVisibility(View.GONE);
            beerANDqty2.setVisibility(View.GONE);
            beerANDqty3.setVisibility(View.GONE);
            beerANDqty4.setVisibility(View.GONE);
            beerANDqty5.setVisibility(View.GONE);
            datetime1.setVisibility(View.GONE);
            datetime2.setVisibility(View.GONE);
            datetime3.setVisibility(View.GONE);
            datetime4.setVisibility(View.GONE);
            datetime5.setVisibility(View.GONE);
            address1.setVisibility(View.GONE);
            address2.setVisibility(View.GONE);
            address3.setVisibility(View.GONE);
            address4.setVisibility(View.GONE);
            address5.setVisibility(View.GONE);
            btnCancelOrder1.setVisibility(View.GONE);
            btnCancelOrder2.setVisibility(View.GONE);
            btnCancelOrder3.setVisibility(View.GONE);
            btnCancelOrder4.setVisibility(View.GONE);
            btnCancelOrder5.setVisibility(View.GONE);
        } else if (ids.size() == 1) {
            orderID1.setText(ids.get(ids.size() - 1).toString());
            beerANDqty1.setText(beerMap.get(ids.get(ids.size() - 1)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            datetime1.setText("On " + datumMap.get(ids.get(ids.size() - 1)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            address1.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 1)));

            orderID2.setVisibility(View.GONE);
            orderID3.setVisibility(View.GONE);
            orderID4.setVisibility(View.GONE);
            orderID5.setVisibility(View.GONE);
            txtIDfixt2.setVisibility(View.GONE);
            txtIDfixt3.setVisibility(View.GONE);
            txtIDfixt4.setVisibility(View.GONE);
            txtIDfixt5.setVisibility(View.GONE);
            beerANDqty2.setVisibility(View.GONE);
            beerANDqty3.setVisibility(View.GONE);
            beerANDqty4.setVisibility(View.GONE);
            beerANDqty5.setVisibility(View.GONE);
            datetime2.setVisibility(View.GONE);
            datetime3.setVisibility(View.GONE);
            datetime4.setVisibility(View.GONE);
            datetime5.setVisibility(View.GONE);
            address2.setVisibility(View.GONE);
            address3.setVisibility(View.GONE);
            address4.setVisibility(View.GONE);
            address5.setVisibility(View.GONE);
            btnCancelOrder2.setVisibility(View.GONE);
            btnCancelOrder3.setVisibility(View.GONE);
            btnCancelOrder4.setVisibility(View.GONE);
            btnCancelOrder5.setVisibility(View.GONE);
        } else if (ids.size() == 2) {
            orderID1.setText(ids.get(ids.size() - 1).toString());
            orderID2.setText(ids.get(ids.size() - 2).toString());
            beerANDqty1.setText(beerMap.get(ids.get(ids.size() - 1)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            beerANDqty2.setText(beerMap.get(ids.get(ids.size() - 2)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            datetime1.setText("On " + datumMap.get(ids.get(ids.size() - 1)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            datetime2.setText("On " + datumMap.get(ids.get(ids.size() - 2)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            address1.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 1)));
            address2.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 2)));

            orderID3.setVisibility(View.GONE);
            orderID4.setVisibility(View.GONE);
            orderID5.setVisibility(View.GONE);
            txtIDfixt3.setVisibility(View.GONE);
            txtIDfixt4.setVisibility(View.GONE);
            txtIDfixt5.setVisibility(View.GONE);
            beerANDqty3.setVisibility(View.GONE);
            beerANDqty4.setVisibility(View.GONE);
            beerANDqty5.setVisibility(View.GONE);
            datetime3.setVisibility(View.GONE);
            datetime4.setVisibility(View.GONE);
            datetime5.setVisibility(View.GONE);
            address3.setVisibility(View.GONE);
            address4.setVisibility(View.GONE);
            address5.setVisibility(View.GONE);
            btnCancelOrder3.setVisibility(View.GONE);
            btnCancelOrder4.setVisibility(View.GONE);
            btnCancelOrder5.setVisibility(View.GONE);
        } else if (ids.size() == 3) {
            orderID1.setText(ids.get(ids.size() - 1).toString());
            orderID2.setText(ids.get(ids.size() - 2).toString());
            orderID3.setText(ids.get(ids.size() - 3).toString());
            beerANDqty1.setText(beerMap.get(ids.get(ids.size() - 1)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            beerANDqty2.setText(beerMap.get(ids.get(ids.size() - 2)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            beerANDqty3.setText(beerMap.get(ids.get(ids.size() - 3)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            datetime1.setText("On " + datumMap.get(ids.get(ids.size() - 1)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            datetime2.setText("On " + datumMap.get(ids.get(ids.size() - 2)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            datetime3.setText("On " + datumMap.get(ids.get(ids.size() - 3)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            address1.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 1)));
            address2.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 2)));
            address3.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 3)));

            orderID4.setVisibility(View.GONE);
            orderID5.setVisibility(View.GONE);
            txtIDfixt4.setVisibility(View.GONE);
            txtIDfixt5.setVisibility(View.GONE);
            beerANDqty4.setVisibility(View.GONE);
            beerANDqty5.setVisibility(View.GONE);
            datetime4.setVisibility(View.GONE);
            datetime5.setVisibility(View.GONE);
            address4.setVisibility(View.GONE);
            address5.setVisibility(View.GONE);
            btnCancelOrder4.setVisibility(View.GONE);
            btnCancelOrder5.setVisibility(View.GONE);
        } else if (ids.size() == 4) {
            orderID1.setText(ids.get(ids.size() - 1).toString());
            orderID2.setText(ids.get(ids.size() - 2).toString());
            orderID3.setText(ids.get(ids.size() - 3).toString());
            orderID4.setText(ids.get(ids.size() - 4).toString());
            beerANDqty1.setText(beerMap.get(ids.get(ids.size() - 1)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            beerANDqty2.setText(beerMap.get(ids.get(ids.size() - 2)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            beerANDqty3.setText(beerMap.get(ids.get(ids.size() - 3)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            beerANDqty4.setText(beerMap.get(ids.get(ids.size() - 4)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            datetime1.setText("On " + datumMap.get(ids.get(ids.size() - 1)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            datetime2.setText("On " + datumMap.get(ids.get(ids.size() - 2)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            datetime3.setText("On " + datumMap.get(ids.get(ids.size() - 3)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            datetime4.setText("On " + datumMap.get(ids.get(ids.size() - 4)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            address1.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 1)));
            address2.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 2)));
            address3.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 3)));
            address4.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 4)));

            orderID5.setVisibility(View.GONE);
            txtIDfixt5.setVisibility(View.GONE);
            beerANDqty5.setVisibility(View.GONE);
            datetime5.setVisibility(View.GONE);
            address5.setVisibility(View.GONE);
            btnCancelOrder5.setVisibility(View.GONE);
        } else {
            orderID1.setText(ids.get(ids.size() - 1).toString());
            orderID2.setText(ids.get(ids.size() - 2).toString());
            orderID3.setText(ids.get(ids.size() - 3).toString());
            orderID4.setText(ids.get(ids.size() - 4).toString());
            orderID5.setText(ids.get(ids.size() - 5).toString());
            beerANDqty1.setText(beerMap.get(ids.get(ids.size() - 1)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            beerANDqty2.setText(beerMap.get(ids.get(ids.size() - 2)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            beerANDqty3.setText(beerMap.get(ids.get(ids.size() - 3)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            beerANDqty4.setText(beerMap.get(ids.get(ids.size() - 4)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            beerANDqty5.setText(beerMap.get(ids.get(ids.size() - 5)) + " " + quantityMap.get(ids.get(ids.size() - 1)) + "x");
            datetime1.setText("On " + datumMap.get(ids.get(ids.size() - 1)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            datetime2.setText("On " + datumMap.get(ids.get(ids.size() - 2)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            datetime3.setText("On " + datumMap.get(ids.get(ids.size() - 3)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            datetime4.setText("On " + datumMap.get(ids.get(ids.size() - 4)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            datetime5.setText("On " + datumMap.get(ids.get(ids.size() - 5)) + " at " + timeslotMap.get(ids.get(ids.size() - 1)));
            address1.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 1)));
            address2.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 2)));
            address3.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 3)));
            address4.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 4)));
            address5.setText("Deliver address: " + addressMap.get(ids.get(ids.size() - 5)));
        }
    }

    public void deleteOrder(int buttonNr) {
        String id = null;
        if (buttonNr == 1) {
            id = orderID1.getText().toString();
        } else if (buttonNr == 2) {
            id = orderID2.getText().toString();
        } else if (buttonNr == 3) {
            id = orderID3.getText().toString();
        } else if (buttonNr == 4) {
            id = orderID4.getText().toString();
        } else if (buttonNr == 5) {
            id = orderID5.getText().toString();
        }
        String requestURL = "https://studev.groept.be/api/a21pt111/deleteOrder/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Order canceled successfully", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Wacht 1 seconde
            }
        }, 1000);
    }

    public void intentAllInvisible()
    {
        orderID1.setVisibility(View.GONE);
        orderID2.setVisibility(View.GONE);
        orderID3.setVisibility(View.GONE);
        orderID4.setVisibility(View.GONE);
        orderID5.setVisibility(View.GONE);
        txtIDfixt1.setVisibility(View.GONE);
        txtIDfixt2.setVisibility(View.GONE);
        txtIDfixt3.setVisibility(View.GONE);
        txtIDfixt4.setVisibility(View.GONE);
        txtIDfixt5.setVisibility(View.GONE);
        beerANDqty1.setVisibility(View.GONE);
        beerANDqty2.setVisibility(View.GONE);
        beerANDqty3.setVisibility(View.GONE);
        beerANDqty4.setVisibility(View.GONE);
        beerANDqty5.setVisibility(View.GONE);
        datetime1.setVisibility(View.GONE);
        datetime2.setVisibility(View.GONE);
        datetime3.setVisibility(View.GONE);
        datetime4.setVisibility(View.GONE);
        datetime5.setVisibility(View.GONE);
        address1.setVisibility(View.GONE);
        address2.setVisibility(View.GONE);
        address3.setVisibility(View.GONE);
        address4.setVisibility(View.GONE);
        address5.setVisibility(View.GONE);
        btnCancelOrder1.setVisibility(View.GONE);
        btnCancelOrder2.setVisibility(View.GONE);
        btnCancelOrder3.setVisibility(View.GONE);
        btnCancelOrder4.setVisibility(View.GONE);
        btnCancelOrder5.setVisibility(View.GONE);
    }
}