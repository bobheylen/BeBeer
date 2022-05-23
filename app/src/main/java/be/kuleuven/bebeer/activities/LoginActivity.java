package be.kuleuven.bebeer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.bebeer.R;

public class LoginActivity extends AppCompatActivity {



    private EditText ETusername;
    private EditText ETpassword;
    private Button btnLogin;
    private TextView txtErrorUsername, txtErrorPassword;

    public static String usernameFromLogin; // Global variable om ook in andere klasses te kunnen gebruiken
    private String password;
    private String passwordFromDB;
    private String passwordIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ETusername = (EditText) findViewById(R.id.invUsername);
        ETpassword = (EditText) findViewById(R.id.invPassword);
        txtErrorPassword = (TextView) findViewById(R.id.txtErrorPassword);
        txtErrorUsername = (TextView) findViewById(R.id.txtErrorUsername);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // place your clicking handle code here.
                if(getTextInParameters()) {
                    usernamePasswordCheck();
                }
                else{
                    txtErrorUsername.setText("Mising password or username");
                    txtErrorPassword.setText("Mising password or username");
                }

            }
        });
    }


    public String usernamePasswordCheck() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/getPasswordFromUsername/" + usernameFromLogin;
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
                            //code om login te doen.
                            if (passwordFromDB.equals(password)) {
                                openHomePageActivity();
                                System.out.println("*********************************************Button werkt na 1 klik****************************************************************");
                            }
                            else{
                                txtErrorPassword.setText("Incorect password");
                            }
                            System.out.println("Password from database: " + passwordFromDB);
                        } catch (JSONException e) {
                            txtErrorPassword.setText("Incorect password or username");
                            txtErrorUsername.setText("Incorect password or username");
                            //Log.e("Database", e.getMessage(), e);
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
        LoginActivity.usernameFromLogin = ETusername.getText().toString(); // Global variable om ook in andere klasses te kunnen gebruiken
        password = ETpassword.getText().toString();
        if (!usernameFromLogin.isEmpty() && !password.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public String getUsernameFromLogin() {
        return usernameFromLogin;
    }

    public void onBtnRegister(View caller) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }


    public void openHomePageActivity() {
        Intent intent = new Intent(this, HomePage2Activity.class);
        startActivity(intent);
    }

    public void openHomePage2Activity(View caller) {
        Intent intent = new Intent(this, HomePage2Activity.class);
        startActivity(intent);
    }
}