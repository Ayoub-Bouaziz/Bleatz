package fr.stvenchg.bleatz;

import android.view.View;

public class SettingsItem {
    private int iconResourceId;
    private String title;
    private View.OnClickListener onClickListener;

    public SettingsItem(int iconResourceId, String title, View.OnClickListener onClickListener) {
        this.iconResourceId = iconResourceId;
        this.title = title;
        this.onClickListener = onClickListener;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public String getTitle() {
        return title;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }
}