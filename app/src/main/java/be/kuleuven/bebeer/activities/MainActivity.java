package be.kuleuven.bebeer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    private EditText ETusername;
    private EditText ETpassword;
    private Button btnLogin;

    private String username;
    private String password;
    private String passwordFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ETusername = (EditText) findViewById(R.id.invUsername);
        ETpassword = (EditText) findViewById(R.id.invPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // place your clicking handle code here.
                if (getTextInParameters()) {
                    usernamePasswordCheck();
                    if (password.equals(usernamePasswordCheck())) {
                        openHomePageActivity();
                        System.out.println("*********************************************Button werkt na 1 klik****************************************************************");
                    }
                }
            }
        });
    }


    public String usernamePasswordCheck() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/getPasswordFromUsername/" + username;
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
                            System.out.println("Password from database: " + passwordFromDB);
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
        return passwordFromDB;
    }


    public boolean getTextInParameters() {
        username = ETusername.getText().toString();
        password = ETpassword.getText().toString();
        if (!username.isEmpty() && !password.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void onBtnRegister(View caller) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void openHomePageActivity() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }
}