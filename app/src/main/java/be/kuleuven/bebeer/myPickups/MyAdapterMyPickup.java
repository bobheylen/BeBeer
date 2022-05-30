package be.kuleuven.bebeer.myPickups;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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

import be.kuleuven.bebeer.R;
import be.kuleuven.bebeer.activities.LoginActivity;
import be.kuleuven.bebeer.activities.RecyclerViewClickInerface;

public class MyAdapterMyPickup extends RecyclerView.Adapter<MyAdapterMyPickup.MyViewHolder> {

    private static final int REQUEST_CALL = 1;
    Context context;
    ArrayList<MyPickup> list;
    private RecyclerViewClickInerface recyclerViewClickInerface;
    private String address;

    public MyAdapterMyPickup(Context context, ArrayList<MyPickup> list, RecyclerViewClickInerface recyclerViewClickInerface) {
        this.context = context;
        this.list = list;
        this.recyclerViewClickInerface = recyclerViewClickInerface;
        getAddress();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_mypickup, parent, false);
        return new MyViewHolder(v);

    }

    public void getAddress() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a21pt111/getAllParamFromMypickup/" + LoginActivity.usernameFromLogin;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);
                                address = curObject.getString("pickupAddress");

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

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyPickup myPickup = list.get(position);
        holder.myPickupUsername.setText(myPickup.getMyPickupUsername());
        holder.myPickupName.setText(myPickup.getMyPickupFirstname() + " " + myPickup.getMyPickupLastname());
        holder.myPickupDate.setText("At " + myPickup.getMyPickupDate() + " between " + myPickup.getMyPickupTime());
        holder.myPickupAddress.setText(myPickup.getMyPickupAddress());
        holder.myPickupQuantity.setText(myPickup.getMyPickupQuantity());
        holder.myPickupPhonenumber.setText(myPickup.getMyPickupPhonenumber());

        holder.btnMaps.setOnClickListener(view -> openGoogleMaps());

        holder.btnCall.setOnClickListener(view -> makePhoneCall());

        holder.btnDelete.setOnClickListener(view -> {
            String requestURL = "https://studev.groept.be/api/a21pt111/deleteMyPickup/" + myPickup.getMyPickupID();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(context, "Pickup: " + myPickup.getMyPickupID() + " successfully deleted", Toast.LENGTH_SHORT).show();
                            recyclerViewClickInerface.updateRecyclerview();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(stringRequest);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void openGoogleMaps() {
        //waar je maps gaat openen
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String coordinatenGroepT = "50.874986,4.707685";
        intent.setData(Uri.parse("geo:0,0?q=" + address));
        Intent chooser = Intent.createChooser(intent, "Launch Maps");
        context.startActivity(chooser);
    }

    private void makePhoneCall() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a21pt111/getAllParamFromMypickup/" + LoginActivity.usernameFromLogin;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);
                                String phonenumber = curObject.getString("phonenumber");

                                //**************************** phonecall ****************************
                                if (ContextCompat.checkSelfPermission(context,
                                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions((Activity) context,
                                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                } else {
                                    String dial = "tel:" + phonenumber;
                                    context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                                }
                                //**************************** phonecall ****************************
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myPickupUsername, myPickupName, myPickupDate, myPickupAddress, myPickupQuantity, myPickupPhonenumber;
        Button btnMaps, btnCall, btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            myPickupUsername = itemView.findViewById(R.id.tvMypickupUsername);
            myPickupName = itemView.findViewById(R.id.tvMypickupName);
            myPickupDate = itemView.findViewById(R.id.tvMypickupDate);
            myPickupAddress = itemView.findViewById(R.id.tvMypickupAddress);
            myPickupQuantity = itemView.findViewById(R.id.tvMypickupQuantity);
            myPickupPhonenumber = itemView.findViewById(R.id.tvMypickupPhonenumber);
            btnMaps = itemView.findViewById(R.id.btnMaps);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

}
