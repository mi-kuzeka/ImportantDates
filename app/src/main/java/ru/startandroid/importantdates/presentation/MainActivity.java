package ru.startandroid.importantdates.presentation;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import ru.startandroid.importantdates.R;
import ru.startandroid.importantdates.core.domain.Event;
import ru.startandroid.importantdates.framework.UserPreferences;
import ru.startandroid.importantdates.presentation.event.EventActivity;
import ru.startandroid.importantdates.presentation.eventlist.EventListFragment;
import ru.startandroid.importantdates.presentation.helpers.MonthsHelper;
import ru.startandroid.importantdates.presentation.months.MonthsFragmentStateAdapter;

public class MainActivity extends AppCompatActivity {
    /**
     * Key for passing Event object to the {@link EventActivity}
     */
    public static final String EVENT_KEY = "event";
    public static final String EVENT_IMAGE_KEY = "event_image";

    /**
     * Key for saving instance state
     */
    public static final String SELECTED_MONTH_KEY = "selected_month";

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get layout for tabs.
        tabLayout = findViewById(R.id.tab_layout);

        // Find the view pager that will allow the user to swipe between fragments.
        viewPager = findViewById(R.id.event_list_container);

        // Create an adapter that knows which fragment should be shown on each page.
        viewPager.setAdapter(new MonthsFragmentStateAdapter(getSupportFragmentManager(),
                getLifecycle()));

        List<String> tabTitles = MonthsHelper.getMonthNames(this);

        // Set titles for tabs.
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(tabTitles.get(position));
        }).attach();

        // Select tab with current month
        if (savedInstanceState == null) {
            selectMonth(MonthsHelper.getCurrentMonth());
        } else {
            int tabIndex = savedInstanceState.getInt(SELECTED_MONTH_KEY);
            selectMonth(tabIndex);
        }

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab_add_event);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            mStartForResult.launch(intent);
        });

        UserPreferences userPreferences = new UserPreferences(this);
        Button sortButton = findViewById(R.id.sort_button);
        sortButton.setText(userPreferences.getStringOrderBy(this));
        setIconToSortButton(sortButton, userPreferences.getOrderBy(this));

        sortButton.setOnClickListener(view -> {
            int orderBy = userPreferences.turnOrderByPreference(MainActivity.this);
            sortButton.setText(userPreferences.getStringOrderBy(orderBy));
            setIconToSortButton(sortButton, orderBy);
            sortEvents();
        });
    }

    public ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    int eventMonth = viewPager.getCurrentItem() + 1;
                    Intent data = result.getData();
                    if (data != null) {
                        Event event = data.getParcelableExtra(MainActivity.EVENT_KEY);
                        if (event != null) eventMonth = event.getMonth();
                    }

                    EventListFragment currentFragment = getFragmentByMonth(eventMonth);
                    if (currentFragment != null)
                        currentFragment.refreshEvents();
                }
            });

    private EventListFragment getFragmentByMonth(int month) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + (month - 1));
        if (fragment == null) return null;
        return (EventListFragment) fragment;
    }

    private int getSelectedMonthPage() {
        return tabLayout.getSelectedTabPosition();
    }

    private void selectMonth(int tabIndex) {
        TabLayout.Tab tabToSelect = tabLayout.getTabAt(tabIndex);
        if (tabToSelect != null)
            tabToSelect.select();
    }

    private void sortEvents() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment :
                fragments) {
            EventListFragment eventListFragment = (EventListFragment) fragment;
            eventListFragment.sortEvents();
        }
    }

    private void setIconToSortButton(Button sortButton, int orderBy) {
        switch (orderBy) {
            case UserPreferences.ORDER_BY_DAY_DESC:
            case UserPreferences.ORDER_BY_NAME_DESC:
                sortButton.setCompoundDrawablesWithIntrinsicBounds(0,
                        0, R.drawable.ic_up, 0);
                break;
            case UserPreferences.ORDER_BY_DAY_ASC:
            case UserPreferences.ORDER_BY_NAME_ASC:
            default:
                sortButton.setCompoundDrawablesWithIntrinsicBounds(0,
                        0, R.drawable.ic_down, 0);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SELECTED_MONTH_KEY, getSelectedMonthPage());
        super.onSaveInstanceState(outState);
    }
}