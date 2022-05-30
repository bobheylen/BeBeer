package be.kuleuven.bebeer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import be.kuleuven.bebeer.R;


public class RegistrationActivity extends AppCompatActivity {
    private EditText ETusername;
    private EditText ETname;
    private EditText ETfirstname;
    private EditText ETbirthdate;
    private EditText ETphonenumber;
    private EditText ETaddress;
    private EditText ETpassword1;
    private EditText ETpassword2;
    private TextView TVpassword2;
    private TextView TVpassword1;
    private TextView TVusername;
    private TextView EThousNr, ETzipCode;

    private Button btnRegister;

    private String username;
    private String name;
    private String firstname;
    private String birthdate;
    private String phonenumber;
    private String address;
    private String password1;
    private String password2;
    private String housNr, zipCode;

    private ArrayList<String> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ETusername = findViewById(R.id.invUsernameAC);
        ETname = findViewById(R.id.invNameAC);
        ETfirstname = findViewById(R.id.invFirstNameAC);
        ETbirthdate = findViewById(R.id.invBirthdateAC);
        ETphonenumber = findViewById(R.id.invPhoneNumAC);
        ETaddress = findViewById(R.id.invAddressAC);
        ETpassword1 = findViewById(R.id.invPasswordAC);
        ETpassword2 = findViewById(R.id.invPasswordAC2);
        TVpassword1 = findViewById(R.id.txtPaswordAC);
        TVpassword2 = findViewById(R.id.txtPasword2AC);
        TVusername = findViewById(R.id.txtUsernameAC);
        EThousNr = findViewById(R.id.invNr);
        ETzipCode = findViewById(R.id.invZip);

        btnRegister = findViewById(R.id.btnRegister);

        getAllUsername();

        btnRegister.setOnClickListener(v -> {
            // place your clicking handle code here.
            getTextInParameters();
            if (password1.equals(password2)) {
                if (password1.length() < 8) {
                    TVpassword1.setText("Password too short!");
                    TVpassword2.setText("Password too short!");
                } else {
                    TVpassword1.setText("");
                    TVpassword2.setText("");
                    if (testUsernameAlreadyExist()) {
                        openHomePageActivity();
                        registerUser();
                        setGlobalVariableUsernameFromLogin();
                    } else {
                        TVusername.setText("Username already exist!");
                    }
                }
            } else {
                TVpassword1.setText("");
                TVpassword2.setText("Passwords do NOT match!");
            }
        });
    }

    private void setGlobalVariableUsernameFromLogin() {
        LoginActivity.usernameFromLogin = username;
    }

    public void openHomePageActivity() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    public void getTextInParameters() {
        username = ETusername.getText().toString();
        name = ETname.getText().toString();
        firstname = ETfirstname.getText().toString();
        birthdate = ETbirthdate.getText().toString();
        phonenumber = ETphonenumber.getText().toString();
        address = ETaddress.getText().toString();
        housNr = EThousNr.getText().toString();
        zipCode = ETzipCode.getText().toString();
        password1 = ETpassword1.getText().toString();
        password2 = ETpassword2.getText().toString();

    }

    public void registerUser() {
        LoginActivity login = new LoginActivity();
        String hashPassword = login.hash(password1);
        StringBuilder sb = new StringBuilder(username)
                .append("/" + name)
                .append("/" + firstname)
                .append("/" + phonenumber)
                .append("/" + hashPassword)
                .append("/" + address + " " + housNr + ", " + zipCode)
                .append("/" + birthdate);
        String requestURL = "https://studev.groept.be/api/a21pt111/registerNewPerson/" + sb;
        //String requestURL = "https://studev.groept.be/api/a21pt111/registerNewPerson/" + username + "/" + name + "/" + firstname + "/" + phonenumber + "/" + password1 + "/" + address + "/" + birthdate;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_LONG).show();
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
    }

    public void getAllUsername() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt111/testUsernameAlreadyExist";
        JsonArrayRequest loginRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);
                                String x = curObject.getString("username");
                                users.add(x);
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
    }

    public boolean testUsernameAlreadyExist() {
        if (users.contains(username)) {
            return false;
        } else {
            return true;
        }
    }
}