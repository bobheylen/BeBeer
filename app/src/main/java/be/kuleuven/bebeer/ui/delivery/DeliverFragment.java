package be.kuleuven.bebeer.ui.delivery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import be.kuleuven.bebeer.R;
import be.kuleuven.bebeer.activities.LoginActivity;
import be.kuleuven.bebeer.databinding.FragmentDeliverBinding;

public class DeliverFragment extends Fragment {

    //----- Aanmaken van fields -----
    private static final String TAG = "CalendarActivity";
    private FragmentDeliverBinding binding;
    private Button btnPlus, btnMinus, btnOrder;
    private TextView lblQty, lblPrice;
    private EditText invAddress;
    private Spinner spBeer, spTimeslot;
    private ImageView imgBeer;
    private CalendarView calendarView;
    private String date;
    //----- Aanmaken van fields -----

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DeliverViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DeliverViewModel.class);

        binding = FragmentDeliverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //---------- Vanaf hier zelf toegevoegd ----------
        btnPlus = root.findViewById(R.id.btnPlus);
        btnMinus = root.findViewById(R.id.btnMinus);
        btnOrder = root.findViewById(R.id.btnMaps);
        lblQty = root.findViewById(R.id.lblQty);
        lblPrice = root.findViewById(R.id.lblThePrice);
        invAddress = root.findViewById(R.id.invAddress);
        spBeer = root.findViewById(R.id.spBeer);
        spTimeslot = root.findViewById(R.id.spTimeslots);
        imgBeer = root.findViewById(R.id.imgBeer);
        calendarView = root.findViewById(R.id.calendarView);

        setNewPrice(); // Set price when creating fragment
        getInfo(); // Set your account address in invAddress

        spBeer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setNewPrice(); // Set price when changing beer
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Always something selected in our case
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String i2s = Integer.toString(i2);
                String i1s = Integer.toString(i1 + 1); // +1 omdat het altijd een maand vroeger was (vb: juni was mei)
                if (i2 <= 9) {
                    i2s = "0" + i2s;
                }
                if (i1 <= 9) {
                    i1s = "0" + i1s;
                }
                date = i2s + ":" + i1s + ":" + i;
                Log.d(TAG, "onSelectedDayChange: date: " + date);
                System.out.println(date);
            }
        });


        btnPlus.setOnClickListener(view -> {
            String username = LoginActivity.usernameFromLogin;
            System.out.println(username);
            int quantity = Integer.parseInt(lblQty.getText().toString()) + 1;
            lblQty.setText(Integer.toString(quantity));
            btnMinus.setEnabled(true);
            btnOrder.setEnabled(true);

            setNewPrice(); // Set price when you change quantity
        });

        btnMinus.setOnClickListener(view -> {
            int quantity = Integer.parseInt(lblQty.getText().toString()) - 1;
            if (quantity == 0) {
                lblQty.setText(Integer.toString(quantity));
                btnMinus.setEnabled(false);
                btnOrder.setEnabled(false);
            } else {
                lblQty.setText(Integer.toString(quantity));
            }

            setNewPrice(); // Set price when you change quantity
        });

        btnOrder.setOnClickListener(view -> placeOrder());
        //---------- Tot hier zelf toegevoegd ----------

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String requestURL = "https://studev.groept.be/api/a21pt111/All_infor_login/" + LoginActivity.usernameFromLogin;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);
                                String address = curObject.getString("address");
                                invAddress.setText(address);
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
        requestQueue.add(request);
    }

    public void placeOrder() {
        StringBuilder sb = new StringBuilder(LoginActivity.usernameFromLogin)
                .append("/" + spBeer.getSelectedItem().toString())
                .append("/" + lblQty.getText().toString())
                .append("/" + lblPrice.getText().toString())
                .append("/" + date)
                .append("/" + spTimeslot.getSelectedItem().toString())
                .append("/" + invAddress.getText().toString());
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String requestURL = "https://studev.groept.be/api/a21pt111/placeOrder/" + sb;
        System.out.println(requestURL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        toastMsg("Order placed succesfully"); // Geeft succes message in App
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

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public void setNewPrice() {
        String price = "";
        double qty = Double.parseDouble(lblQty.getText().toString());
        String beer = spBeer.getSelectedItem().toString();
        switch (beer) {
            case "StellaArtois":
                double priceStella = 19.75;
                price = qty * priceStella + "";
                lblPrice.setText(price);
                imgBeer.setImageResource(R.drawable.bakstellanobackground);
                break;
            case "Jupiler":
                double priceJupiler = 21.25;
                price = qty * priceJupiler + "";
                lblPrice.setText(price);
                imgBeer.setImageResource(R.drawable.bakjupilernobackground);
                break;
            case "Maes":
                double priceMaes = 11.25;
                price = qty * priceMaes + "";
                lblPrice.setText(price);
                imgBeer.setImageResource(R.drawable.bakmaesnobackground);
                break;
            case "Cristal":
                double priceCristal = 31.25;
                price = qty * priceCristal + "";
                lblPrice.setText(price);
                imgBeer.setImageResource(R.drawable.bakcristalnobackground);
                break;
            case "Primus":
                double pricePrimus = 15.25;
                price = qty * pricePrimus + "";
                lblPrice.setText(price);
                imgBeer.setImageResource(R.drawable.bakprimusnobackground);
                break;
            case "Cara":
                double priceCara = 1.25;
                price = qty * priceCara + "";
                lblPrice.setText(price);
                imgBeer.setImageResource(R.drawable.bakcaranobackground);
                break;
            default:
                break;
        }
    }

}