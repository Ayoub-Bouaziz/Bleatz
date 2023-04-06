package fr.stvenchg.bleatz.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.activity.AddAddressActivity;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.account.AccountResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private TextView bonjourTextView;
    private String firstname;

    private LinearLayout addAddressBar;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String firstname) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bonjourTextView = view.findViewById(R.id.fhome_textview_bonjour);
        addAddressBar = view.findViewById(R.id.fhome_linearlayout_addaddress);

        bonjourTextView.setText("Salut " + firstname + ",");

        addAddressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddAddressActivity.class);
                startActivity(intent);
            }
        });

    }

}