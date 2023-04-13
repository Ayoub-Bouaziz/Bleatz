package fr.stvenchg.bleatz.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.listener.ValidateOrderListener;
import fr.stvenchg.bleatz.adapter.ContentAdapter;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.kitchen.OrderContentResponse;
import fr.stvenchg.bleatz.api.kitchen.ValidateOrderResponse;
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

    private Button orderReadyButton;

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
        orderReadyButton = findViewById(R.id.order_button_orderready);

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

        orderReadyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                builder.setTitle("La commande est prête ?");
                builder.setMessage("Veuillez confirmer que la commande est prête.");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        validateOrder(idOrder, new ValidateOrderListener() {
                            @Override
                            public void onOrderValidated(ValidateOrderResponse response) {
                                Toast.makeText(OrderActivity.this, "Commande prête ! Le client a été notifié.", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onOrderValidationFailed(Throwable t) {
                                Toast.makeText(OrderActivity.this, "Erreur lors de la validation de la commande. Veuillez réessayer.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                // Personnaliser les couleurs des boutons
                int buttonColor = ContextCompat.getColor(OrderActivity.this, R.color.orange_300); // Remplacez R.color.orange par la référence de couleur souhaitée
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(buttonColor);
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(buttonColor);
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

    private void validateOrder(int orderId, ValidateOrderListener listener) {
        AuthenticationManager authenticationManager = new AuthenticationManager(this);
        String accessToken = authenticationManager.getAccessToken();

        if (accessToken != null) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ValidateOrderResponse> call = apiInterface.validateOrder("Bearer " + accessToken, orderId);

            call.enqueue(new Callback<ValidateOrderResponse>() {
                @Override
                public void onResponse(Call<ValidateOrderResponse> call, Response<ValidateOrderResponse> response) {
                    if (response.isSuccessful()) {
                        ValidateOrderResponse validateOrderResponse = response.body();
                        listener.onOrderValidated(validateOrderResponse);
                    }
                }

                @Override
                public void onFailure(Call<ValidateOrderResponse> call, Throwable t) {
                    listener.onOrderValidationFailed(t);
                }
            });
        }
    }
}