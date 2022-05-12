package be.kuleuven.bebeer.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.bebeer.R;
import be.kuleuven.bebeer.databinding.FragmentAccountBinding;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private String username = "bobheylen";
    private String passwordFromDB;
    private TextView txtUsernameAC;
    private Button btnAccount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel notificationsViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // vanaf hier zelf geschriven
        btnAccount = (Button) root.findViewById(R.id.btnAccount); //waarom zeten we een root?_______________________________________?
        txtUsernameAC = (TextView) root.findViewById(R.id.txtUsernameAC);

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                                    txtUsernameAC.setText(passwordFromDB);
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
        });

        //tot heir geschrven

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}