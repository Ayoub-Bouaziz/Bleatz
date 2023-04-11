package fr.stvenchg.bleatz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.adapter.ContentAdapter;
import fr.stvenchg.bleatz.adapter.OrderAdapter;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.kitchen.KitchenResponse;
import fr.stvenchg.bleatz.api.kitchen.OrderContentResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    private OrderContentResponse orderContentResponse;
    private int idOrder;
    private String orderStatut;
    private String orderDate;
    private int orderUserId;
    private TextView orderIdTextView;
    private TextView orderStatutTextView;
    private TextView orderDateTextView;
    private TextView orderUserIdTextView;
    private RecyclerView recyclerView;
    private ContentAdapter contentAdapter;
    private List<OrderContentResponse.CommandeContent> contentList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Intent intent = getIntent();
        idOrder = intent.getIntExtra("order_id", 0);
        orderStatut = intent.getStringExtra("order_statut");
        orderDate = intent.getStringExtra("order_date");
        orderUserId = intent.getIntExtra("order_user_id", 0);

        if (orderStatut.equals("processing")) {
            orderStatut = "À préparer";
        }

        orderIdTextView = findViewById(R.id.order_id);
        orderStatutTextView = findViewById(R.id.order_statut);
        orderDateTextView = findViewById(R.id.order_date);
        orderUserIdTextView = findViewById(R.id.order_user);

        orderIdTextView.setText("Commande n°" + String.valueOf(idOrder));
        orderStatutTextView.setText(orderStatut);
        orderDateTextView.setText("Date : " + orderDate);
        orderUserIdTextView.setText("Destinataire : " + String.valueOf(orderUserId));

        recyclerView = findViewById(R.id.content_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderContentResponse = new OrderContentResponse();

        contentList = new ArrayList<>();
        contentAdapter = new ContentAdapter(contentList,this);
        fetchContent();


        recyclerView.setAdapter(contentAdapter);
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

    private void fetchContent() {
        AuthenticationManager authenticationManager = new AuthenticationManager(this);
        String accessToken = authenticationManager.getAccessToken();

        if (accessToken != null) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            retrofit2.Call<OrderContentResponse> call = apiInterface.getOrderContent("Bearer " + accessToken, idOrder);

            call.enqueue(new Callback<OrderContentResponse>() {
                @Override
                public void onResponse(retrofit2.Call<OrderContentResponse> call, Response<OrderContentResponse> response) {
                    if (response.isSuccessful()) {
                        contentList.clear();
                        orderContentResponse = response.body();
                        contentList.addAll(orderContentResponse.getContent());
                        contentAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<OrderContentResponse> call, Throwable t) {

                }
            });
        }
    }
}