package ru.startandroid.importantdates.presentation.months;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ru.startandroid.importantdates.presentation.eventlist.EventListFragment;
import ru.startandroid.importantdates.presentation.helpers.MonthsHelper;

public class MonthsFragmentStateAdapter extends FragmentStateAdapter {
    public MonthsFragmentStateAdapter(@NonNull FragmentManager fragmentManager,
                                      @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Create fragment for each month.
        if (position >= 0 && position < MonthsHelper.getMonthsCount()) {
            return EventListFragment.newInstance(position + 1);
        } else {
            return null;
        }
    }


    @Override
    public int getItemCount() {
        return MonthsHelper.getMonthsCount();
    }
}
