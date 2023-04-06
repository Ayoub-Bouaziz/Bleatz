package fr.stvenchg.bleatz.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.activity.AuthActivity;
import fr.stvenchg.bleatz.activity.RegisterActivity;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.account.AccountResponse;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    private TextView bonjourTextView;

    private String firstname;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(String firstname) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString("firstname", firstname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            firstname = getArguments().getString("firstname");
        }
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
        bonjourTextView.setText(firstname);

        Button logoutButton = view.findViewById(R.id.faccount_button_logout);
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

        /*
        Button cuisineButton = view.findViewById(R.id.faccount_button_cuisine);
        if (utilisateur.getRole().equals("cuisine")) {
            cuisineButton.setVisibility(View.VISIBLE);
        } else {
            cuisineButton.setVisibility(View.GONE);
        }
        */

    }



}