package fr.stvenchg.bleatz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fr.stvenchg.bleatz.fragment.FinishedOrdersFragment;
import fr.stvenchg.bleatz.fragment.ProcessingOrdersFragment;

public class UserOrderPagerAdapter extends FragmentStateAdapter {

    public UserOrderPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ProcessingOrdersFragment();
        } else {
            return new FinishedOrdersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}