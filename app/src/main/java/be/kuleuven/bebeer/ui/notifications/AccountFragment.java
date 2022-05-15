package be.kuleuven.bebeer.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
import be.kuleuven.bebeer.databinding.FragmentAccountBinding;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private String username = "bobheylen";
    private EditText invUsernameAC;

    private Button btnSaveAC;
    private String firstName;
    private EditText invFirstnameAC;
    private String name;
    private EditText invNameAC;
    private String birthdate;
    private EditText invbirthdateAC;
    private int number;
    private EditText invPhoneNumberAC;
    private String address;
    private EditText invAddressAC;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel notificationsViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //vanaf hier zelf geschrven

        invNameAC = (EditText) root.findViewById(R.id.invNameAC);
        invFirstnameAC = (EditText) root.findViewById(R.id.invFirstNameAC);
        invbirthdateAC = (EditText) root.findViewById(R.id.invBirthdateAC);
        invPhoneNumberAC = (EditText) root.findViewById(R.id.invPhoneNumAC);
        invAddressAC = (EditText) root.findViewById(R.id.invAddressAC);
        invUsernameAC = (EditText) root.findViewById(R.id.invUsernameAC);


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
                                number = curObject.getInt("phonenumber");
                                address = curObject.getString("address");
                            }
                            invUsernameAC.setHint(username);
                            invFirstnameAC.setHint(firstName);
                            invNameAC.setHint(name);
                            invbirthdateAC.setHint(birthdate);
                            invPhoneNumberAC.setHint(number);
                            invAddressAC.setHint(address);
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

        //---------- Vanaf hier zelf toegevoegd ----------
        btnSaveAC = (Button) root.findViewById(R.id.btnSaveAC); //waarom zeten we een root?_______________________________________?



        btnSaveAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // place your clicking handle code here.
                save();
            }
        });
        //---------- Tot hier zelf toegevoegd ----------


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void save() {

    }


}