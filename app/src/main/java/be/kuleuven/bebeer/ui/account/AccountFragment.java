package be.kuleuven.bebeer.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

import be.kuleuven.bebeer.myPickups.MyPickupActivity;
import be.kuleuven.bebeer.myOrders.OrderActivity;
import be.kuleuven.bebeer.R;
import be.kuleuven.bebeer.activities.LoginActivity;
import be.kuleuven.bebeer.databinding.FragmentAccountBinding;


public class AccountFragment extends Fragment {

    private final ArrayList<String> users = new ArrayList<>();
    private FragmentAccountBinding binding;
    private String username = LoginActivity.usernameFromLogin;
    private EditText invUsernameAC;
    private Button btnSaveAC, btnOrders, btnMypickups;
    private String firstName;
    private EditText invFirstnameAC;
    private String name;
    private EditText invNameAC;
    private String birthdate;
    private EditText invbirthdateAC;
    private String phoneNum;
    private EditText invPhoneNumberAC;
    private String address;
    private EditText invAddressAC;
    private String password;
    private EditText invPassword;
    private String password2;
    private EditText invPassword2;
    private TextView txtPaswordAC;
    private TextView txtPasword2AC;
    private TextView txtUsernameAC;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel notificationsViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //vanaf hier zelf geschrven

        invNameAC = root.findViewById(R.id.invNameAC);
        invFirstnameAC = root.findViewById(R.id.invFirstNameAC);
        invbirthdateAC = root.findViewById(R.id.invBirthdateAC);
        invPhoneNumberAC = root.findViewById(R.id.invPhoneNumAC);
        invAddressAC = root.findViewById(R.id.invAddressAC);
        invUsernameAC = root.findViewById(R.id.invUsernameAC);
        btnSaveAC = root.findViewById(R.id.btnSaveAC);
        btnOrders = root.findViewById(R.id.btnOrders);
        btnMypickups = root.findViewById(R.id.btnMypickups);
        invPassword = root.findViewById(R.id.invPasswordAC);
        invPassword2 = root.findViewById(R.id.invPasswordAC2);
        txtPaswordAC = root.findViewById(R.id.txtPaswordAC);
        txtPasword2AC = root.findViewById(R.id.txtPasword2AC);
        txtUsernameAC = root.findViewById(R.id.txtUsernameAC);


        getInfo();
        getAllUsername();

        btnSaveAC.setOnClickListener(view -> {
            // place your clicking handle code here.
            getTextInParameters();
            if (password.equals(password2)) {
                if (password.length() < 8) {
                    txtPaswordAC.setText("Password too short!");
                    txtPasword2AC.setText("Password too short!");
                } else {
                    txtPaswordAC.setText("");
                    txtPasword2AC.setText("");
                    if (testUsernameAlreadyExist()) {
                        save();
                    } else {
                        txtUsernameAC.setText("Username already exist!");
                    }
                }
            } else {
                txtPaswordAC.setText("");
                txtPasword2AC.setText("Passwords do NOT match!");
            }
        });

        btnOrders.setOnClickListener(view -> openOrderActivity());

        btnMypickups.setOnClickListener(view -> openMypickupActivity());
        //---------- Tot hier zelf toegevoegd ----------


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getTextInParameters() {
        username = invUsernameAC.getText().toString();
        name = invNameAC.getText().toString();
        firstName = invFirstnameAC.getText().toString();
        birthdate = invbirthdateAC.getText().toString();
        phoneNum = invPhoneNumberAC.getText().toString();
        address = invAddressAC.getText().toString();
        password = invPassword.getText().toString();
        password2 = invPassword2.getText().toString();
    }

    public void save() {

        if (!(invPassword.getText().toString() == "")) {
            LoginActivity LA = new LoginActivity();
            password = LoginActivity.hash(invPassword.getText().toString());
            password2 = LoginActivity.hash(invPassword.getText().toString());
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringBuilder sb = new StringBuilder(name)
                .append("/" + firstName)
                .append("/" + phoneNum)
                .append("/" + password)
                .append("/" + address)
                .append("/" + birthdate)
                .append("/" + username);

        System.out.println(sb);
        String requestURL = "https://studev.groept.be/api/a21pt111/updateAccount/" + sb;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        toastMsg("Updated account successfully"); // Geeft succes message in App
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toastMsg("error:" + error.getMessage()); // Geeft error message in App
                    }
                });
        requestQueue.add(stringRequest);
    }

    public void getInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String requestURL = "https://studev.groept.be/api/a21pt111/All_infor_login/" + username;
        JsonArrayRequest loginRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);
                                name = curObject.getString("name");
                                firstName = curObject.getString("firstname");
                                birthdate = curObject.getString("birthdate");
                                phoneNum = curObject.getString("phonenumber");
                                address = curObject.getString("address");
                                password = curObject.getString("password");
                                System.out.println("name:" + name + "firstname: " + firstName + "birthdate: " + birthdate + "phoneNum: " + birthdate);


                            }
                            invUsernameAC.setText(username);
                            invFirstnameAC.setText(firstName);
                            invNameAC.setText(name);
                            invbirthdateAC.setText(birthdate);
                            invPhoneNumberAC.setText(phoneNum);
                            invAddressAC.setText(address);
                            invPassword.setText("");
                            invPassword2.setText("");
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

    public void getAllUsername() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
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
        if (username.equals(LoginActivity.usernameFromLogin)) {
            return true;
        } else {
            return !users.contains(username);
        }
    }


    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public void openOrderActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), OrderActivity.class);
        startActivity(intent);
    }

    public void openMypickupActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), MyPickupActivity.class);
        startActivity(intent);
    }

}