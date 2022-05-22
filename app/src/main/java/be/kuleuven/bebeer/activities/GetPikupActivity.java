package be.kuleuven.bebeer.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class GetPikupActivity extends AppCompatActivity {

    private Button btnPlus, btnMin, btnPlacePick;
    private TextView txtAmountOfBack;
    private String username = LoginActivity.usernameFromLogin, address;
    private EditText invAddres;
    private Spinner spTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pikup);

        btnPlus = (Button) findViewById(R.id.btnPlusBack);
        btnMin = (Button) findViewById(R.id.btnMinBack);
        txtAmountOfBack = (TextView) findViewById(R.id.txtAmountOfBack);
        btnPlacePick = (Button) findViewById(R.id.btnGetPikupPK);
        invAddres = (EditText) findViewById(R.id.invAddresPikup);
        spTime = (Spinner) findViewById(R.id.spTimePikup);

        //zelf geschrve
        getInfo();


        btnPlacePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placePickUp();
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(txtAmountOfBack.getText().toString()) + 1;
                txtAmountOfBack.setText(Integer.toString(quantity));
                btnMin.setEnabled(true);
                btnPlacePick.setEnabled(true);
            }
        });

        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(txtAmountOfBack.getText().toString()) - 1;
                if (quantity == 0)
                {
                    txtAmountOfBack.setText(Integer.toString(quantity));
                    btnMin.setEnabled(false);
                    btnPlacePick.setEnabled(false);
                }
                else
                {
                    txtAmountOfBack.setText(Integer.toString(quantity));
                }
            }

        });

    }

    public void getInfo()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/All_infor_login/" + username;
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
                .append("/" + spTime.getSelectedItem().toString());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/insertPikup/" + sb;
        System.out.println(requestURL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        toastMsg("Pikup at " + spTime.getSelectedItem().toString() + " sucsais");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toastMsg("Pikup failed");
                    }
                });
        requestQueue.add(stringRequest);
    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }



}