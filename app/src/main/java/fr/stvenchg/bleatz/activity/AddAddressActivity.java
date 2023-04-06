package fr.stvenchg.bleatz.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;

import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.GooglePlacesApiService;
import fr.stvenchg.bleatz.api.PlacesAutocompleteResponse;
import fr.stvenchg.bleatz.api.SuggestionsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAddressActivity extends AppCompatActivity {

    private AutoCompleteTextView searchBar;
    private RecyclerView suggestionsList;
    private SuggestionsAdapter suggestionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddress);

        // Initialize the Google Places SDK
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDcUCQj0LefK5FGbQ6J0hOagVOwoLD_JH0");
        }

        searchBar = findViewById(R.id.search_bar);
        suggestionsList = findViewById(R.id.suggestions_list);

        suggestionsAdapter = new SuggestionsAdapter(this);

        suggestionsList.setLayoutManager(new LinearLayoutManager(this));
        suggestionsList.setAdapter(suggestionsAdapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update suggestions
                fetchSuggestions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
    }