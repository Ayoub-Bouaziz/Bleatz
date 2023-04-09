package fr.stvenchg.bleatz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        recyclerView = findViewById(R.id.order_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        kitchenResponse = new KitchenResponse();
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList);
        fetchOrders();

        recyclerView.setAdapter(orderAdapter);


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
