package fr.stvenchg.bleatz.fragment;

import android.os.Bundle;
import android.os.Handler;
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
import android.content.Intent;
import fr.stvenchg.bleatz.activity.UserOrderDetailsActivity;
import fr.stvenchg.bleatz.activity.UserOrderTrackActivity;

public class ProcessingOrdersFragment extends Fragment implements UserOrderAdapter.OnOrderClickListener {

    private RecyclerView recyclerView;
    private UserOrderAdapter userOrderAdapter;
    private List<Order> orders = new ArrayList<>();

    public ProcessingOrdersFragment() {
        super(R.layout.fragment_processingorders);
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
        setOrders(orders);
    }

    @Override
    public void onOrderClick(Order order) {
        Intent intent = new Intent(getActivity(), UserOrderTrackActivity.class);
        intent.putExtra("order_id", order.idCommande);
        intent.putExtra("order_date", order.dateCommande);

        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}