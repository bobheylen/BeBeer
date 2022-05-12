package be.kuleuven.bebeer.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.bebeer.R;

public class PikupActivity extends AppCompatActivity {

    private String username = "bobheylen";
    private String passwordFromDB;
    private TextView txtPikupPI;
    private Button btnPicupPI;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pikup);

        txtPikupPI = (TextView) findViewById(R.id.txtPikupPI);
        btnPicupPI = (Button) findViewById(R.id.btnPicupPI);

        btnPicupPI.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              watUser();
              System.out.println("knop werkt");
          }
    });
/*
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/All_infor_login/" + username;
        JsonArrayRequest loginRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String responseString = "";
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);
                                responseString += curObject.getString("password");
                            }
                            passwordFromDB = responseString;
                            txtPikupPI.setText(passwordFromDB);
                        } catch (JSONException e) {
                            Log.e("Database", e.getMessage(), e);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(loginRequest);
 */
    }


    public void watUser(){
            // place your clicking handle code here
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String requestURL = "https://studev.groept.be/api/a21pt111/All_infor_login/" + username;
            JsonArrayRequest loginRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                String responseString = "";
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject curObject = response.getJSONObject(i);
                                    responseString += curObject.getString("password");
                                }
                                passwordFromDB = responseString;
                                txtPikupPI.setText(passwordFromDB);
                            } catch (JSONException e) {
                                Log.e("Database", e.getMessage(), e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            requestQueue.add(loginRequest);
    }
}


