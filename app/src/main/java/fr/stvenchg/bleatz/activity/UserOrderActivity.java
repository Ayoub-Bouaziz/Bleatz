package fr.stvenchg.bleatz.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.Order;
import fr.stvenchg.bleatz.OrderWrapper;
import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.adapter.UserOrderPagerAdapter;
import fr.stvenchg.bleatz.listener.UserOrdersListener;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.orders.OrdersResponse;
import fr.stvenchg.bleatz.fragment.FinishedOrdersFragment;
import fr.stvenchg.bleatz.fragment.ProcessingOrdersFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOrderActivity extends AppCompatActivity implements UserOrdersListener {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private AuthenticationManager authenticationManager;

    private List<Order> processingOrders = new ArrayList<>();
    private List<Order> finishedOrders = new ArrayList<>();

    private TextView noOrdersText;

    private Handler handler = new Handler();

    private boolean isFetchingOrders = false;

    private static final long FETCH_ORDERS_DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userorder);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        noOrdersText = findViewById(R.id.noOrdersTextView);
        noOrdersText.setVisibility(View.GONE);

        authenticationManager = new AuthenticationManager(this);

        setupViewPager();
        fetchOrders();
        updateOrders();
    }

    @Override
    public void onOrdersFetched(List<Order> processingOrders, List<Order> finishedOrders) {
        this.processingOrders.clear();
        this.processingOrders.addAll(processingOrders);
        this.finishedOrders.clear();
        this.finishedOrders.addAll(finishedOrders);

        if (processingOrders.isEmpty() && finishedOrders.isEmpty()) {
            noOrdersText.setVisibility(View.VISIBLE);
        } else {
            noOrdersText.setVisibility(View.GONE);
        }

        // Mettre à jour les fragments avec les nouvelles commandes
        UserOrderPagerAdapter userOrderPagerAdapter = (UserOrderPagerAdapter) viewPager.getAdapter();
        if (userOrderPagerAdapter != null) {
            ProcessingOrdersFragment processingOrdersFragment = userOrderPagerAdapter.getProcessingOrdersFragment();
            FinishedOrdersFragment finishedOrdersFragment = userOrderPagerAdapter.getFinishedOrdersFragment();

            if (processingOrdersFragment != null) {
                processingOrdersFragment.updateOrders(processingOrders);
            }

            if (finishedOrdersFragment != null) {
                finishedOrdersFragment.updateOrders(finishedOrders);
            }
        }
    }

    private void setupViewPager() {
        UserOrderPagerAdapter userOrderPagerAdapter = new UserOrderPagerAdapter(this);
        viewPager.setAdapter(userOrderPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("En cours");
            } else {
                tab.setText("Terminées");
            }
        }).attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                UserOrderPagerAdapter userOrderPagerAdapter = (UserOrderPagerAdapter) viewPager.getAdapter();
                if (userOrderPagerAdapter != null) {
                    if (position == 0) {
                        ProcessingOrdersFragment processingOrdersFragment = userOrderPagerAdapter.getProcessingOrdersFragment();
                        if (processingOrdersFragment != null) {
                            processingOrdersFragment.updateOrders(processingOrders);
                        }
                    } else {
                        FinishedOrdersFragment finishedOrdersFragment = userOrderPagerAdapter.getFinishedOrdersFragment();
                        if (finishedOrdersFragment != null) {
                            finishedOrdersFragment.updateOrders(finishedOrders);
                        }
                    }
                }
            }
        });
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
        String accessToken = authenticationManager.getAccessToken();
        if (accessToken != null) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<OrdersResponse> call = apiInterface.getOrders("Bearer " + accessToken);

            call.enqueue(new Callback<OrdersResponse>() {
                @Override
                public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                    if (response.isSuccessful()) {
                        OrdersResponse ordersResponse = response.body();
                        if (ordersResponse != null && ordersResponse.isSuccess()) {
                            List<OrderWrapper> orderWrappers = ordersResponse.getOrders();
                            if (!orderWrappers.isEmpty()) {
                                OrderWrapper orderWrapper = orderWrappers.get(0);
                                List<Order> processingOrders = orderWrapper.processing;
                                List<Order> finishedOrders = orderWrapper.finished;
                                onOrdersFetched(processingOrders, finishedOrders);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<OrdersResponse> call, Throwable t) {
                    Log.d("UserOrderActivity", "API call failed with error: " + t.getMessage()); // Ajouter cette ligne
                }
            });
        }
    }

    private void updateOrders() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchOrders();
                updateOrders();
            }
        }, FETCH_ORDERS_DELAY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchOrdersPeriodically();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
        isFetchingOrders = false;
    }

    private void fetchOrdersPeriodically() {
        if (isFetchingOrders) {
            // Une requête est déjà en cours
            return;
        }
        isFetchingOrders = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchOrders();
                isFetchingOrders = false;
                handler.postDelayed(this, FETCH_ORDERS_DELAY);
            }
        }, FETCH_ORDERS_DELAY);
    }
}
