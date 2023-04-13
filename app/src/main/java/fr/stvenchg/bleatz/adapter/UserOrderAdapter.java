package fr.stvenchg.bleatz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.Order;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.OrderViewHolder> {

    private List<Order> orders;
    private Context context;

    private OnOrderClickListener onOrderClickListener;

    public UserOrderAdapter(List<Order> orders, Context context, OnOrderClickListener onOrderClickListener) {
        this.orders = orders;
        this.context = context;
        this.onOrderClickListener = onOrderClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        RecyclerView recyclerView = (RecyclerView) parent;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildLayoutPosition(v);
                if (onOrderClickListener != null && position != RecyclerView.NO_POSITION) {
                    onOrderClickListener.onOrderClick(orders.get(position).idCommande);
                }
            }
        });
        return new OrderViewHolder(view, recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderId.setText("Commande n°" + String.valueOf(order.idCommande));
        holder.orderDate.setText("Passée le : " + order.dateCommande);

        if (order.statut.equals("processing")) {
            holder.orderStatut.setText("En préparation");
        } else if (order.statut.equals("finished")) {
            holder.orderStatut.setText("Terminée");
            holder.orderStatut.setTextColor(ContextCompat.getColor(context, R.color.green_300));
        } else {
            holder.orderStatut.setText(order.statut);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId;
        TextView orderDate;
        TextView orderStatut;
        RecyclerView recyclerView;

        public OrderViewHolder(@NonNull View itemView, RecyclerView recyclerView) {
            super(itemView);
            this.recyclerView = recyclerView;
            orderId = itemView.findViewById(R.id.order_id);
            orderDate = itemView.findViewById(R.id.order_date);
            orderStatut = itemView.findViewById(R.id.order_status);
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(int orderId);
    }
}