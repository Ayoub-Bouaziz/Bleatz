package fr.stvenchg.bleatz.listener;

import com.google.android.libraries.places.api.model.AutocompletePrediction;

public interface OnSuggestionClickListener {
    void onSuggestionClick(AutocompletePrediction prediction);
}