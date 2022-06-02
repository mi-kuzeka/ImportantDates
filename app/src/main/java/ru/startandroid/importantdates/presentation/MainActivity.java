package ru.startandroid.importantdates.presentation;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import ru.startandroid.importantdates.R;
import ru.startandroid.importantdates.presentation.event.EventActivity;
import ru.startandroid.importantdates.presentation.eventlist.EventListFragment;
import ru.startandroid.importantdates.presentation.helpers.MonthsHelper;
import ru.startandroid.importantdates.presentation.months.MonthsFragmentStateAdapter;

public class MainActivity extends AppCompatActivity {
    /**
     * Key for passing Event object to the {@link EventActivity}
     */
    public static final String EVENT_KEY = "event";
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get layout for tabs.
        TabLayout tabLayout = findViewById(R.id.tab_layout);

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
        tabLayout.getTabAt(MonthsHelper.getCurrentMonth()).select();

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab_add_event);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            mStartForResult.launch(intent);
        });
    }

    public ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    getCurrentFragment().refreshEvents();
                }
            });

    private EventListFragment getCurrentFragment() {
        return (EventListFragment)
                getSupportFragmentManager().findFragmentByTag("f" + viewPager.getCurrentItem());
    }
}