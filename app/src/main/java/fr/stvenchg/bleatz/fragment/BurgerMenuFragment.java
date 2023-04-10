package fr.stvenchg.bleatz.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.activity.AuthActivity;
import fr.stvenchg.bleatz.adapter.BurgerAdapter;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.account.AccountResponse;
import fr.stvenchg.bleatz.api.burger.BurgerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BurgerMenuFragment extends Fragment {

    private RecyclerView recyclerView;
    private BurgerAdapter adapter;
    private BurgerResponse burgerResponse;

    private List<BurgerResponse.Burger> burgers;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_burger_menu, container, false);



        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        burgerResponse = new BurgerResponse();
        burgers = new ArrayList<>();

        adapter = new BurgerAdapter(burgers,getActivity());

        fetchBurgers();
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void fetchBurgers() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call<BurgerResponse> call = apiInterface.getBurgers();
        call.enqueue(new Callback<BurgerResponse>() {
            @Override
            public void onResponse(Call<BurgerResponse> call, Response<BurgerResponse> response) {
                burgers.clear();
                burgers.addAll(response.body().getBurgers());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<BurgerResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Erreur: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

}
