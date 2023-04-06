package fr.stvenchg.bleatz.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesApiService {
    @GET("place/autocomplete/json")
    Call<PlacesAutocompleteResponse> getPlaceSuggestions(@Query("input") String input, @Query("key") String apiKey);
}