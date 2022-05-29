package be.kuleuven.bebeer.pickups;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import be.kuleuven.bebeer.R;
import be.kuleuven.bebeer.activities.LoginActivity;
import be.kuleuven.bebeer.activities.RecyclerViewClickInerface;

import java.util.ArrayList;

public class MyAdapterPickup extends RecyclerView.Adapter<MyAdapterPickup.MyViewHolder> {

    Context context;
    ArrayList<Pickup> list;
    private RecyclerViewClickInerface recyclerViewClickInerface;

    public MyAdapterPickup(Context context, ArrayList<Pickup> list, RecyclerViewClickInerface recyclerViewClickInerface) {
        this.context = context;
        this.list = list;
        this.recyclerViewClickInerface = recyclerViewClickInerface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_pickup, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pickup pickup = list.get(position);
        holder.pickupUsername.setText(pickup.getPickupUsername());
        holder.pickupAddress.setText(pickup.getPickupAddress());
        holder.pickupDate.setText("At " + pickup.getPickupDate() + " between " + pickup.getPickupTime());
        holder.pickupQuantity.setText(pickup.getPickupQuantity());

        holder.btnPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //****************************************************************
                //   ********** Delete pick-up from database pickups **********
                String requestURL = "https://studev.groept.be/api/a21pt111/deletePickup/" + pickup.getPickupID();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(context,"Pickup: " + pickup.getPickupID() + " successfully deleted from database", Toast.LENGTH_SHORT).show();
                                Log.d("MyAdapterPickup", "***** Pickup successfully deleted from database *****");
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
                //   ********** Delete pick-up from database pickups **********
                //****************************************************************

                //****************************************************************
                //   ********** Insert pick-up in database myPickups **********
                StringBuilder sb = new StringBuilder(LoginActivity.usernameFromLogin)
                        .append("/" + pickup.getPickupAddress())
                        .append("/" + pickup.getPickupQuantity())
                        .append("/" + pickup.getPickupDate())
                        .append("/" + pickup.getPickupTime())
                        .append("/" + pickup.getPickupUsername());
                String requestURL2 = "https://studev.groept.be/api/a21pt111/addPickupToMyPickups/" + sb;
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, requestURL2,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context, "Pickup: " + pickup.getPickupID() + " successfully added to my pick-ups", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                RequestQueue queue2 = Volley.newRequestQueue(context);
                queue2.add(stringRequest2);
                //   ********** Insert pick-up in database myPickups **********
                //****************************************************************
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pickupUsername, pickupAddress, pickupDate, pickupQuantity;
        Button btnPickup;

        public MyViewHolder(@NonNull View itenView) {
            super(itenView);

            pickupUsername = itemView.findViewById(R.id.tvPickupUsername);
            pickupAddress = itemView.findViewById(R.id.tvPickupAddress);
            pickupDate = itemView.findViewById(R.id.tvPickupDate);
            pickupQuantity = itemView.findViewById(R.id.tvPickupQuantity);
            btnPickup = itenView.findViewById(R.id.btnPickup);
        }

    }

}
