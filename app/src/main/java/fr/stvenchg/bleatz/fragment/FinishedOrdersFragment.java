package fr.stvenchg.bleatz.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.Order;
import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.adapter.OrderAdapter;
import fr.stvenchg.bleatz.adapter.UserOrderAdapter;

public class FinishedOrdersFragment extends Fragment implements UserOrderAdapter.OnOrderClickListener {

    private RecyclerView recyclerView;
    private UserOrderAdapter userOrderAdapter;
    private List<Order> orders = new ArrayList<>();

    public FinishedOrdersFragment() {
        super(R.layout.fragment_finishedorders);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        userOrderAdapter = new UserOrderAdapter(orders, getContext(), this);
        recyclerView.setAdapter(userOrderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setOrders(List<Order> orders) {
        this.orders.clear();
        this.orders.addAll(orders);
        if (userOrderAdapter != null) {
            userOrderAdapter.notifyDataSetChanged();
        }
    }

    public void updateOrders(List<Order> orders) {
        Log.d("FinishedOrdersFragment", "Updating orders: " + orders.size());
        setOrders(orders);
    }

    @Override
    public void onOrderClick(int orderId) {
        Toast.makeText(getContext(), "Commande n°" + orderId, Toast.LENGTH_SHORT).show();
    }
}