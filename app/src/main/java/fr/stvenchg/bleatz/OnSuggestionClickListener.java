package fr.stvenchg.bleatz;

import com.google.android.libraries.places.api.model.AutocompletePrediction;

public interface OnSuggestionClickListener {
    void onSuggestionClick(AutocompletePrediction prediction);
}