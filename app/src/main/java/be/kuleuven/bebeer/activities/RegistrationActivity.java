package be.kuleuven.bebeer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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

        ETusername = (EditText) findViewById(R.id.invUsername2);
        ETname = (EditText) findViewById(R.id.invName);
        ETfirstname = (EditText) findViewById(R.id.invFirstName);
        ETbirthdate = (EditText) findViewById(R.id.invBirthdate);
        ETphonenumber = (EditText) findViewById(R.id.invPhoneNum);
        ETaddress = (EditText) findViewById(R.id.invAddress);
        ETpassword1 = (EditText) findViewById(R.id.invPassword2);
        ETpassword2 = (EditText) findViewById(R.id.invPassword3);
        TVpassword1 = (TextView) findViewById(R.id.blanco5);
        TVpassword2 = (TextView) findViewById(R.id.blanco8);

        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // place your clicking handle code here.
                getTextInParameters();
                if (password1.equals(password2))
                {
                    if (password1.length() < 8) {
                        TVpassword1.setText("Password too short!");
                        TVpassword2.setText("Password too short!");
                    }
                    else
                    {
                        TVpassword1.setText("");
                        TVpassword2.setText("");
                        openHomePageActivity();
                        //makeInsertRequest("https://studev.groept.be/api/a21pt111/TEST/" + username + "/" + name + "/" + firstname + "/" + phonenumber + "/" + password1 + "/" + address + "/" + birthdate);
                    }
                }
                else
                {
                    TVpassword1.setText("");
                    TVpassword2.setText("Passwords do NOT match!");
                }
            }
        });
    }

    public String makeInsertRequest(String urlName) {
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;
        try {
            URL url = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line + '\n');
            }
            conn.disconnect();
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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
        password1 = ETpassword1.getText().toString();
        password2 = ETpassword2.getText().toString();
    }
}