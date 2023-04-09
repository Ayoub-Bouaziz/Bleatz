package fr.stvenchg.bleatz.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.kitchen.KitchenResponse;
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<KitchenResponse.Order> orderList;

    public OrderAdapter(List<KitchenResponse.Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        KitchenResponse.Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView orderIdTextView;
        private TextView orderDateTextView;
        private TextView orderStatusTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.order_id_text_view);
            orderDateTextView = itemView.findViewById(R.id.order_date_text_view);
            orderStatusTextView = itemView.findViewById(R.id.order_status_text_view);
        }

        public void bind(KitchenResponse.Order order) {
            orderIdTextView.setText("Commande " + String.valueOf(order.getIdCommande()));
            orderDateTextView.setText(order.getDateCommande());
            orderStatusTextView.setText(order.getStatut());
        }



    }
}


