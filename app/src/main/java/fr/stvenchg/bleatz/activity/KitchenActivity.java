package fr.stvenchg.bleatz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.adapter.OrderAdapter;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.account.AccountResponse;
import fr.stvenchg.bleatz.api.kitchen.KitchenResponse;
import fr.stvenchg.bleatz.fragment.HomeFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KitchenActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<KitchenResponse.Order> orderList;
    private KitchenResponse kitchenResponse;

    private Handler handler;

    private Runnable updateOrdersRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.order_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        kitchenResponse = new KitchenResponse();
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList,this);
        fetchOrders();

        recyclerView.setAdapter(orderAdapter);

        handler = new Handler();
        updateOrdersRunnable = new Runnable() {
            @Override
            public void run() {
                fetchOrders();
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(updateOrdersRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateOrdersRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(updateOrdersRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        restartUpdateOrders();
    }

    private void restartUpdateOrders() {
        handler.removeCallbacks(updateOrdersRunnable);
        handler.post(updateOrdersRunnable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fetchOrders() {
        AuthenticationManager authenticationManager = new AuthenticationManager(this);
        String accessToken = authenticationManager.getAccessToken();

        if (accessToken != null) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            retrofit2.Call<KitchenResponse> call = apiInterface.getKitchenOrders("Bearer " + accessToken);

            call.enqueue(new Callback<KitchenResponse>() {
                @Override
                public void onResponse(retrofit2.Call<KitchenResponse> call, Response<KitchenResponse> response) {
                    if (response.isSuccessful()) {
                        orderList.clear();
                        kitchenResponse = response.body();
                        orderList.addAll(kitchenResponse.getOrders());
                        orderAdapter.notifyDataSetChanged();
                    }
                }


                @Override
                public void onFailure(Call<KitchenResponse> call, Throwable t) {

                }


            });
        }
    }



}
