package ru.startandroid.importantdates.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import ru.startandroid.importantdates.R;
import ru.startandroid.importantdates.presentation.helpers.MonthsHelper;
import ru.startandroid.importantdates.presentation.months.MonthsFragmentStateAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get layout for tabs.
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        // Find the view pager that will allow the user to swipe between fragments.
        ViewPager2 viewPager = findViewById(R.id.event_list_container);

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

    }
}