package fr.stvenchg.bleatz.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.order.OneOrderDetailsResponse;
import fr.stvenchg.bleatz.api.order.OrderDetailsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOrderTrackActivity extends AppCompatActivity {
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView toolbarTitle;

    private TextView orderDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordertrack);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Trouver la vue qui agit en tant que BottomSheet
        LinearLayout bottomSheetView = findViewById(R.id.order_bottom_sheet);
        // Associer un BottomSheetBehavior à la vue
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);

        int idCommande = getIntent().getIntExtra("order_id", 0);
        String dateCommande = getIntent().getStringExtra("order_date");

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Commande n°" + idCommande);

        orderDate = findViewById(R.id.ordertrack_date);
        orderDate.setText("Commande passée : " + dateCommande);
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

    private void fetchOneOrderDetails(int orderId) {

        AuthenticationManager authenticationManager = new AuthenticationManager(this);
        String accessToken = authenticationManager.getAccessToken();

        if (accessToken != null) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<OneOrderDetailsResponse> call = apiInterface.getOneOrderDetails("Bearer " + accessToken, orderId);

            call.enqueue(new Callback<OneOrderDetailsResponse>() {
                @Override
                public void onResponse(Call<OneOrderDetailsResponse> call, Response<OneOrderDetailsResponse> response) {
                    if (response.isSuccessful()) {
                        OneOrderDetailsResponse oneOrderDetailsResponse = response.body();
                        if (oneOrderDetailsResponse.isSuccess()) {
                            // Utilisez les données de la réponse ici
                        } else {
                            // Gérez l'échec ici
                        }
                    } else {
                        // Gérez l'échec de la requête ici
                    }
                }
                @Override
                public void onFailure(Call<OneOrderDetailsResponse> call, Throwable t) {
                    // Gérez l'échec de la connexion ici
                }
            });
        }
    }
}