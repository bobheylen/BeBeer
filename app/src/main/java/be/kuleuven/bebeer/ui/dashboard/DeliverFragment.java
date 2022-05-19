package be.kuleuven.bebeer.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import be.kuleuven.bebeer.R;
import be.kuleuven.bebeer.activities.LoginActivity;
import be.kuleuven.bebeer.databinding.FragmentDeliverBinding;

public class DeliverFragment extends Fragment {

    private FragmentDeliverBinding binding;
    //----- Aanmaken van fields -----
    private static final String TAG = "CalendarActivity";
    private Button btnPlus, btnMinus, btnOrder;
    private TextView lblQty,lblPrice;
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
        btnOrder = root.findViewById(R.id.btnOrder);
        lblQty = root.findViewById(R.id.lblQty);
        lblPrice = root.findViewById(R.id.lblThePrice);
        spBeer = root.findViewById(R.id.spBeer);
        spTimeslot = root.findViewById(R.id.spTimeslots);
        imgBeer = root.findViewById(R.id.imgBeer);
        calendarView = root.findViewById(R.id.calendarView);

        setNewPrice(); // Set price when creating fragment

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
                date = i2 + ":" + i1 + ":" + i;
                Log.d(TAG, "onSelectedDayChange: date: " + date);
                System.out.println(date);
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = LoginActivity.usernameFromLogin;
                System.out.println(username);
                int quantity = Integer.parseInt(lblQty.getText().toString()) + 1;
                lblQty.setText(Integer.toString(quantity));
                btnMinus.setEnabled(true);
                btnOrder.setEnabled(true);

                setNewPrice(); // Set price when you change quantity
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(lblQty.getText().toString()) - 1;
                if (quantity == 0)
                {
                    lblQty.setText(Integer.toString(quantity));
                    btnMinus.setEnabled(false);
                    btnOrder.setEnabled(false);
                }
                else
                {
                    lblQty.setText(Integer.toString(quantity));
                }

                setNewPrice(); // Set price when you change quantity
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder();
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

    public void placeOrder() {
        StringBuilder sb = new StringBuilder(LoginActivity.usernameFromLogin)
                .append("/" + spBeer.getSelectedItem().toString())
                .append("/" + lblQty.getText().toString())
                .append("/" + lblPrice.getText().toString())
                .append("/" + date)
                .append("/" + spTimeslot.getSelectedItem().toString());
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

    public void setNewPrice()
    {
        String price = "";
        double qty = Double.parseDouble(lblQty.getText().toString());
        String beer = spBeer.getSelectedItem().toString();
        switch (beer) {
            case "StellaArtois":
                double priceStella = 19.75;
                price = qty*priceStella+"";
                lblPrice.setText(price);
                imgBeer.setImageResource(R.drawable.bakstellanobackground);
                break;
            case "Jupiler":
                double priceJupiler = 21.25;
                price = qty*priceJupiler+"";
                lblPrice.setText(price);
                imgBeer.setImageResource(R.drawable.bakjupilernobackground);
                break;
            case "Maes":
                double priceMaes = 11.25;
                price = qty*priceMaes+"";
                lblPrice.setText(price);
                imgBeer.setImageResource(R.drawable.bakmaesnobackground);
                break;
            case "Cristal":
                double priceCristal = 31.25;
                price = qty*priceCristal+"";
                lblPrice.setText(price);
                imgBeer.setImageResource(R.drawable.bakcristalnobackground);
                break;
            case "Primus":
                double pricePrimus = 15.25;
                price = qty*pricePrimus+"";
                lblPrice.setText(price);
                imgBeer.setImageResource(R.drawable.bakprimusnobackground);
                break;
            case "Cara":
                double priceCara = 1.25;
                price = qty*priceCara+"";
                lblPrice.setText(price);
                imgBeer.setImageResource(R.drawable.bakcaranobackground);
                break;
            default:
                break;
        }
    }
}