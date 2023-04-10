package fr.stvenchg.bleatz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;

import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.GooglePlacesApiService;
import fr.stvenchg.bleatz.api.PlacePrediction;
import fr.stvenchg.bleatz.api.PlacesAutocompleteResponse;
import fr.stvenchg.bleatz.api.SuggestionsAdapter;
import fr.stvenchg.bleatz.api.set.SetAddressRequest;
import fr.stvenchg.bleatz.api.set.SetAddressResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAddressActivity extends AppCompatActivity {
    private RecyclerView suggestionsList;
    private SuggestionsAdapter suggestionsAdapter;
    private AutoCompleteTextView addressAutocompleteView;
    private ImageButton clearImageButton;

    private ImageButton goBackImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddress);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Initialize the Google Places SDK
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDcUCQj0LefK5FGbQ6J0hOagVOwoLD_JH0");
        }

        addressAutocompleteView = findViewById(R.id.addaddress_autocompletetextview_address);

        clearImageButton = findViewById(R.id.addaddress_imagebutton_clear);
        clearImageButton.setVisibility(View.INVISIBLE);

        suggestionsList = findViewById(R.id.suggestions_list);

        suggestionsAdapter = new SuggestionsAdapter(this, suggestionClickListener);

        suggestionsList.setLayoutManager(new LinearLayoutManager(this));
        suggestionsList.setAdapter(suggestionsAdapter);

        clearImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressAutocompleteView.setText("");
            }
        });

        addressAutocompleteView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Afficher les suggestions
                fetchSuggestions(s.toString());

                // Afficher ou masquer le bouton vider texte
                if (s.length() > 0) {
                    clearImageButton.setVisibility(View.VISIBLE);
                } else {
                    clearImageButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
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

    private void fetchSuggestions(String query) {
        // Build Retrofit client and API service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GooglePlacesApiService apiService = retrofit.create(GooglePlacesApiService.class);

            Call<PlacesAutocompleteResponse> call = apiService.getPlaceSuggestions(query, "AIzaSyDcUCQj0LefK5FGbQ6J0hOagVOwoLD_JH0");
            call.enqueue(new Callback<PlacesAutocompleteResponse>() {
            @Override
            public void onResponse(Call<PlacesAutocompleteResponse> call, Response<PlacesAutocompleteResponse> response) {
                if (response.isSuccessful()) {
                    PlacesAutocompleteResponse autocompleteResponse = response.body();
                    if (autocompleteResponse != null) {
                        suggestionsAdapter.updateData(autocompleteResponse.getPredictions());
                    }
                }
            }

                @Override
                public void onFailure(Call<PlacesAutocompleteResponse> call, Throwable t) {
                    Log.e("API_FAILURE", "Request failed: " + t.getMessage());
                }
            });
        }

    private final SuggestionsAdapter.OnSuggestionClickListener suggestionClickListener = new SuggestionsAdapter.OnSuggestionClickListener() {
        @Override
        public void onSuggestionClick(PlacePrediction prediction) {
            String address = prediction.getDescription();
            AuthenticationManager authenticationManager = new AuthenticationManager(AddAddressActivity.this);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<SetAddressResponse> call = apiInterface.setAddress(
                    "Bearer " + authenticationManager.getAccessToken(),
                    new SetAddressRequest(address)
            );

            call.enqueue(new Callback<SetAddressResponse>() {
                @Override
                public void onResponse(Call<SetAddressResponse> call, Response<SetAddressResponse> response) {
                    if (response.isSuccessful()) {
                        SetAddressResponse updateAddressResponse = response.body();
                        if (updateAddressResponse != null && updateAddressResponse.isSuccess()) {
                            Toast.makeText(AddAddressActivity.this, updateAddressResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("address", address);
                            setResult(RESULT_OK, resultIntent);

                            authenticationManager.setAddress(address);
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SetAddressResponse> call, Throwable t) {

                }
            });
        }
    };
    }
