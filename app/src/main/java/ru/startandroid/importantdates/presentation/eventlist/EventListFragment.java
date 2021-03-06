package ru.startandroid.importantdates.presentation.eventlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ru.startandroid.importantdates.R;
import ru.startandroid.importantdates.core.domain.Event;
import ru.startandroid.importantdates.framework.ImportantDatesViewModelFactory;
import ru.startandroid.importantdates.framework.UserPreferences;
import ru.startandroid.importantdates.presentation.customview.EmptyRecyclerView;

/**
 * A fragment representing a list of Items.
 */
public class EventListFragment extends Fragment {

    private final String LOG_TAG = EventListFragment.class.getSimpleName();
    private static final String MONTH_NUMBER = "monthNumber";

    private int mMonthNumber;

    EventListViewModel viewModel;
    private EventsRecyclerViewAdapter recyclerViewAdapter;

    List<Event> events;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventListFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param monthNumber Number of current month.
     * @return A new instance of fragment {@link EventListFragment}.
     */
    public static EventListFragment newInstance(int monthNumber) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putInt(MONTH_NUMBER, monthNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mMonthNumber = getArguments().getInt(MONTH_NUMBER);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            ImportantDatesViewModelFactory viewModelFactory =
                    ImportantDatesViewModelFactory.getInstance();
            viewModelFactory.create(EventListViewModel.class);

            viewModel = new ViewModelProvider(this, viewModelFactory)
                    .get(EventListViewModel.class);

            viewModel.events.observe(getViewLifecycleOwner(),
                    this::setEvents);

            viewModel.loadEvents(mMonthNumber);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error with loading events");
        }

        View rootView;

        // Set layout with list of events.
        rootView = inflater.inflate(R.layout.event_list, container, false);

        if (events == null) events = new ArrayList<>();

        EmptyRecyclerView recyclerView = rootView.findViewById(R.id.event_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        // Set content for each event item.
        recyclerViewAdapter =
                new EventsRecyclerViewAdapter(recyclerView, events, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setEmptyView(rootView.findViewById(R.id.empty_view));

        return rootView;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        if (this.events.size() > 0) {
            sortEvents();
            return;
        }
        refreshRecyclerView();
    }

    public void sortEvents() {
        UserPreferences userPreferences = new UserPreferences(getContext());
        int orderBy = userPreferences.getOrderBy(getContext());
        switch (orderBy) {
            case UserPreferences.ORDER_BY_DAY_ASC:
                events.sort(Comparator.comparingInt(Event::getDay)
                        .thenComparing(Event::getName, String.CASE_INSENSITIVE_ORDER));
                break;
            case UserPreferences.ORDER_BY_DAY_DESC:
                events.sort(Comparator.comparingInt(Event::getDay).reversed()
                        .thenComparing(Event::getName, String.CASE_INSENSITIVE_ORDER));
                break;
            case UserPreferences.ORDER_BY_NAME_ASC:
                events.sort(Comparator.comparing(Event::getName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Event::getDay));
                break;
            case UserPreferences.ORDER_BY_NAME_DESC:
                events.sort(Comparator.comparing(Event::getName, String.CASE_INSENSITIVE_ORDER)
                        .reversed().thenComparing(Event::getDay));
                break;
            default:
                events.sort(Comparator.comparingInt(Event::getDay));
        }
        refreshRecyclerView();
    }

    private void refreshRecyclerView() {
        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.update(this.events);
        }
    }

    public void refreshEvents() {
        viewModel.loadEvents(mMonthNumber);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}