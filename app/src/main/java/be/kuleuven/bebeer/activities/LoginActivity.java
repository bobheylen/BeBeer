package be.kuleuven.bebeer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLOutput;

import be.kuleuven.bebeer.R;

public class LoginActivity extends AppCompatActivity {


    private EditText ETusername;
    private EditText ETpassword;
    private Button btnLogin;
    private TextView txtErrorUsername, txtErrorPassword;

    public static String usernameFromLogin; // Global variable om ook in andere klasses te kunnen gebruiken
    private String password;
    private String passwordFromDB, hashPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ETusername = (EditText) findViewById(R.id.invUsername);
        ETpassword = (EditText) findViewById(R.id.invPassword);
        txtErrorPassword = (TextView) findViewById(R.id.txtErrorPassword);
        txtErrorUsername = (TextView) findViewById(R.id.txtErrorUsername);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            // place your clicking handle code here.
            if (getTextInParameters()) {
                usernamePasswordCheck();
            } else {
                txtErrorUsername.setText("Mising password or username");
                txtErrorPassword.setText("Mising password or username");
            }
        });
    }

    public static String hash(String ToHashPassword) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digestMes = md.digest(ToHashPassword.getBytes());

            BigInteger bigInt = new BigInteger(1, digestMes);
            System.out.println("hasPassword ingegeven:" + bigInt.toString(16));
            return bigInt.toString(16);
        } catch (Exception e) {
        }
        return "";
    }


    public void usernamePasswordCheck() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/getPasswordFromUsername/" + usernameFromLogin;
        JsonArrayRequest loginRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);
                                passwordFromDB = curObject.getString("password");
                            }
                            //***** code om login te doen *****
                            hashPassword = hash(password);
                            if (passwordFromDB.equals(hashPassword)) {
                                openHomePageActivity();
                            } else {
                                txtErrorPassword.setText("Incorrect password");
                            }
                            System.out.println("Password from database: " + passwordFromDB);
                        } catch (JSONException e) {
                            txtErrorPassword.setText("Incorrect password or username");
                            txtErrorUsername.setText("Incorrect password or username");
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
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

}