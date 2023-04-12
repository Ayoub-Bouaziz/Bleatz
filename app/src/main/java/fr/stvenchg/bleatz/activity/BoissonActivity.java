package fr.stvenchg.bleatz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.adapter.BoissonAdapter;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.boisson.BoissonResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoissonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private BoissonResponse boissonResponse;

    private List<BoissonResponse.Boisson> boissons;
    private BoissonAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boisson);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        boissonResponse = new BoissonResponse();
        boissons = new ArrayList<>();

        adapter = new BoissonAdapter(boissons,this);

        fetchBoissons();
        recyclerView.setAdapter(adapter);


    }

    public void fetchBoissons() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call<BoissonResponse> call = apiInterface.getBoissons();
        call.enqueue(new Callback<BoissonResponse>() {
            @Override
            public void onResponse(Call<BoissonResponse> call, Response<BoissonResponse> response) {
                boissons.clear();
                boissons.addAll(response.body().getBoissons());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<BoissonResponse> call, Throwable t) {

            }

        });
    }

}