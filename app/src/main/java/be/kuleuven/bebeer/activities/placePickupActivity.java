package be.kuleuven.bebeer.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
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

import be.kuleuven.bebeer.R;

public class placePickupActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";
    private Button btnPlus, btnMin, btnPlacePick;
    private TextView txtAmountOfBack;
    private String address, date;
    private EditText invAddres;
    private Spinner spTime;
    private CalendarView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_pickup);

        btnPlus = (Button) findViewById(R.id.btnPlusBack);
        btnMin = (Button) findViewById(R.id.btnMinBack);
        txtAmountOfBack = (TextView) findViewById(R.id.txtAmountOfBack);
        btnPlacePick = (Button) findViewById(R.id.btnGetPikupPK);
        invAddres = (EditText) findViewById(R.id.invAddresPikup);
        spTime = (Spinner) findViewById(R.id.spTimePikup);
        dateView = (CalendarView) findViewById(R.id.dateView);

        getInfo();

        btnPlacePick.setOnClickListener(view -> placePickUp());

        btnPlus.setOnClickListener(view -> {
            int quantity = Integer.parseInt(txtAmountOfBack.getText().toString()) + 1;
            txtAmountOfBack.setText(Integer.toString(quantity));
            btnMin.setEnabled(true);
            btnPlacePick.setEnabled(true);
        });

        btnMin.setOnClickListener(view -> {
            int quantity = Integer.parseInt(txtAmountOfBack.getText().toString()) - 1;
            if (quantity == 0) {
                txtAmountOfBack.setText(Integer.toString(quantity));
                btnMin.setEnabled(false);
                btnPlacePick.setEnabled(false);
            } else {
                txtAmountOfBack.setText(Integer.toString(quantity));
            }
        });

        dateView.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            String i2s = Integer.toString(i2);
            String i1s = Integer.toString(i1 + 1); // +1 omdat het altijd een maand vroeger was (vb: juni was mei)
            if (i2 <= 9) {
                i2s = "0" + i2s;
            }
            if (i1 <= 9) {
                i1s = "0" + i1s;
            }
            date = i2s + ":" + i1s + ":" + i;
            Log.d(TAG, "onSelectedDayChange: date: " + date);
            System.out.println(date);
        });

    }

    public void getInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/All_infor_login/" + LoginActivity.usernameFromLogin;
        JsonArrayRequest loginRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);
                                address = curObject.getString("address");
                            }
                            invAddres.setText(address);


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
    }

    public void placePickUp() {
        StringBuilder sb = new StringBuilder(LoginActivity.usernameFromLogin)
                .append("/" + invAddres.getText().toString())
                .append("/" + txtAmountOfBack.getText().toString())
                .append("/" + spTime.getSelectedItem().toString())
                .append("/" + date);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/insertPikup/" + sb;
        System.out.println(requestURL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        toastMsg("Pick-up at " + spTime.getSelectedItem().toString() + " succeed");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toastMsg("Pick-up failed");
                    }
                });
        requestQueue.add(stringRequest);
    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }


}