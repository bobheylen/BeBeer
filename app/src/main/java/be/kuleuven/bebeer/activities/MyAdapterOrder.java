package be.kuleuven.bebeer.activities;

import android.content.Context;
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

import java.util.ArrayList;

public class MyAdapterOrder extends RecyclerView.Adapter<MyAdapterOrder.MyViewHolder>{

    Context context;
    ArrayList<Order> list;

    public MyAdapterOrder(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_order,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = list.get(position);
        holder.orderID.setText(order.getOrderID());
        holder.orderQuantity.setText(order.getOrderQuantity());
        holder.orderDate.setText(order.getOrderDate());
        holder.orderAddress.setText(order.getOrderAddress());

        holder.btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String requestURL = "https://studev.groept.be/api/a21pt111/deleteOrder/" + order.getOrderID();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context,"Order: " + order.getOrderID() + " successfully canceled", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView orderID, orderQuantity, orderDate, orderAddress;
        Button btnCancelOrder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderID = itemView.findViewById(R.id.tvOrderID);
            orderQuantity = itemView.findViewById(R.id.tvOrderQuantity);
            orderDate = itemView.findViewById(R.id.tvOrderDate);
            orderAddress = itemView.findViewById(R.id.tvOrderAddress);
            btnCancelOrder = itemView.findViewById(R.id.btnCancelOrder);
        }
    }
}
