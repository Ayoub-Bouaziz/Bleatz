package fr.stvenchg.bleatz.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.SuggestionViewHolder> {

    private final Context context;
    private final List<PlacePrediction> predictions;
    private final OnSuggestionClickListener suggestionClickListener = null;

    public SuggestionsAdapter(Context context) {
        this.context = context;
        this.predictions = new ArrayList<>();
    }

    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.suggestion_item, parent, false);
        return new SuggestionViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return predictions.size();
    }

    public class SuggestionViewHolder extends RecyclerView.ViewHolder {

        TextView suggestionText;

        public SuggestionViewHolder(@NonNull View itemView) {
            super(itemView);
            suggestionText = itemView.findViewById(R.id.suggestion_text);
        }
    }

    public void updateData(List<PlacePrediction> newPredictions) {
        predictions.clear();
        predictions.addAll(newPredictions);
        notifyDataSetChanged();
    }

    public interface OnSuggestionClickListener {
        void onSuggestionClick(PlacePrediction prediction);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position) {
        // Set the suggestion text
        PlacePrediction prediction = predictions.get(position);
        holder.suggestionText.setText(prediction.getDescription());

        // Set the click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the onSuggestionClick method of the listener
                if (suggestionClickListener != null) {
                    suggestionClickListener.onSuggestionClick(prediction);
                }
            }
        });
    }
}