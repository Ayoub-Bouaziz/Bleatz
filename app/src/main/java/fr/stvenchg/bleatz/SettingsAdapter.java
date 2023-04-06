package fr.stvenchg.bleatz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.stvenchg.bleatz.R;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder> {

    private List<SettingsItem> settingsItems;

    public SettingsAdapter(List<SettingsItem> settingsItems) {
        this.settingsItems = settingsItems;
    }

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_item, parent, false);
        return new SettingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
        SettingsItem item = settingsItems.get(position);
        holder.settingIcon.setImageResource(item.getIconResourceId());
        holder.settingTitle.setText(item.getTitle());
        holder.itemView.setOnClickListener(item.getOnClickListener());
    }

    @Override
    public int getItemCount() {
        return settingsItems.size();
    }

    public static class SettingsViewHolder extends RecyclerView.ViewHolder {

        ImageView settingIcon;
        TextView settingTitle;

        public SettingsViewHolder(@NonNull View itemView) {
            super(itemView);
            settingIcon = itemView.findViewById(R.id.settingIcon);
            settingTitle = itemView.findViewById(R.id.settingTitle);
        }
    }
}