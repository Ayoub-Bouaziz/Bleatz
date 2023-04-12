package fr.stvenchg.bleatz.fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.SettingsAdapter;
import fr.stvenchg.bleatz.SettingsItem;
import fr.stvenchg.bleatz.activity.AccountInfosActivity;
import fr.stvenchg.bleatz.activity.AuthActivity;
import fr.stvenchg.bleatz.activity.RegisterActivity;
import fr.stvenchg.bleatz.activity.UserOrderActivity;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.activity.KitchenActivity;
import fr.stvenchg.bleatz.api.AuthenticationManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    private AuthenticationManager authenticationManager;
    private TextView bonjourTextView;

    private String firstname;
    private String role;

    private String lastname;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(String firstname, String lastname, String role) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString("firstname", firstname);
        args.putString("lastname", lastname);
        args.putString("role", role);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            firstname = getArguments().getString("firstname");
            lastname = getArguments().getString("lastname");
            role = getArguments().getString("role");
        }

        authenticationManager = new AuthenticationManager(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bonjourTextView = view.findViewById(R.id.faccount_textview_nom);
        bonjourTextView.setText(firstname + " " + lastname);

        Button logoutButton = view.findViewById(R.id.faccount_button_logout);

        RecyclerView settingsRecyclerView = view.findViewById(R.id.faccount_recyclerview_settings);
        List<SettingsItem> settingsItems = new ArrayList<>();

        settingsItems.add(new SettingsItem(R.drawable.ic_fastfood, "Mes commandes", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), UserOrderActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }));

        settingsItems.add(new SettingsItem(R.drawable.ic_offer, "Mes offres", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action
            }
        }));

        settingsItems.add(new SettingsItem(R.drawable.ic_account, "Mes informations", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AccountInfosActivity.class);
                intent.putExtra("idUser", authenticationManager.getUserId());
                intent.putExtra("firstname", authenticationManager.getFirstname());
                intent.putExtra("lastname", authenticationManager.getLastname());
                intent.putExtra("phone", authenticationManager.getPhone());
                intent.putExtra("email", authenticationManager.getEmail());
                intent.putExtra("address", authenticationManager.getAddress());

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }));

        SettingsAdapter settingsAdapter = new SettingsAdapter(settingsItems);
        settingsRecyclerView.setAdapter(settingsAdapter);
        settingsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    AuthenticationManager authenticationManager = new AuthenticationManager(getActivity());
                    authenticationManager.clearTokens();

                    Toast.makeText(getActivity(), "Déconnecté.", Toast.LENGTH_SHORT).show();

                    // Rediriger vers AuthActivity
                    Intent intent = new Intent(requireActivity(), AuthActivity.class);
                    startActivity(intent);
                    requireActivity().finishAffinity(); // Ferme l'activité actuelle
                }
            }});


        Button cuisineButton = view.findViewById(R.id.faccount_button_cuisine);
        if (role.equals("cuisine")) {
            cuisineButton.setVisibility(View.VISIBLE);


            cuisineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Créer un Intent pour naviguer vers Activity2
                    Intent intent = new Intent(getActivity(), KitchenActivity.class);
                    startActivity(intent);
                }
            });

        } else {
            cuisineButton.setVisibility(View.GONE);
        }








    }



}