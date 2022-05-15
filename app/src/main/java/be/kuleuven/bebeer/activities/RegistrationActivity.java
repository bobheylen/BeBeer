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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

    private Button btnRegister;

    private String username;
    private String name;
    private String firstname;
    private String birthdate;
    private String phonenumber;
    private String address;
    private String password1;
    private String password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ETusername = (EditText) findViewById(R.id.invUsernameAC);
        ETname = (EditText) findViewById(R.id.invNameAC);
        ETfirstname = (EditText) findViewById(R.id.invFirstNameAC);
        ETbirthdate = (EditText) findViewById(R.id.invBirthdateAC);
        ETphonenumber = (EditText) findViewById(R.id.invPhoneNumAC);
        ETaddress = (EditText) findViewById(R.id.invAddressAC);
        ETpassword1 = (EditText) findViewById(R.id.invPasswordAC);
        ETpassword2 = (EditText) findViewById(R.id.invPasswordAC2);
        TVpassword1 = (TextView) findViewById(R.id.blanco5);
        TVpassword2 = (TextView) findViewById(R.id.blanco8);

        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // place your clicking handle code here.
                getTextInParameters();
                if (password1.equals(password2)) {
                    if (password1.length() < 8) {
                        TVpassword1.setText("Password too short!");
                        TVpassword2.setText("Password too short!");
                    } else {
                        TVpassword1.setText("");
                        TVpassword2.setText("");
                        openHomePageActivity();
                        registerUser();
                    }
                } else {
                    TVpassword1.setText("");
                    TVpassword2.setText("Passwords do NOT match!");
                }
            }
        });
    }

    public void openHomePageActivity() {
        Intent intent = new Intent(this, HomePage2Activity.class);
        startActivity(intent);
    }

    public void getTextInParameters() {
        username = ETusername.getText().toString();
        name = ETname.getText().toString();
        firstname = ETfirstname.getText().toString();
        birthdate = ETbirthdate.getText().toString();
        phonenumber = ETphonenumber.getText().toString();
        address = ETaddress.getText().toString();
        password1 = ETpassword1.getText().toString();
        password2 = ETpassword2.getText().toString();
    }

    public void registerUser() {
        StringBuilder sb = new StringBuilder(username)
            .append("/" + name)
            .append("/" + firstname)
            .append("/" + phonenumber)
            .append("/" + password1)
            .append("/" + address)
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
}