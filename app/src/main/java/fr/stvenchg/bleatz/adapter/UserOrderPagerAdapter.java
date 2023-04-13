package fr.stvenchg.bleatz.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fr.stvenchg.bleatz.fragment.FinishedOrdersFragment;
import fr.stvenchg.bleatz.fragment.ProcessingOrdersFragment;

public class UserOrderPagerAdapter extends FragmentStateAdapter {

    private ProcessingOrdersFragment processingOrdersFragment;
    private FinishedOrdersFragment finishedOrdersFragment;

    public UserOrderPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            processingOrdersFragment = new ProcessingOrdersFragment();
            return processingOrdersFragment;
        } else {
            finishedOrdersFragment = new FinishedOrdersFragment();
            return finishedOrdersFragment;
        }
    }

    public ProcessingOrdersFragment getProcessingOrdersFragment() {
        return processingOrdersFragment;
    }

    public FinishedOrdersFragment getFinishedOrdersFragment() {
        return finishedOrdersFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}