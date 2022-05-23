package ru.startandroid.importantdates.presentation.eventlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.startandroid.importantdates.core.domain.Event;
import ru.startandroid.importantdates.framework.ImportantDatesViewModel;
import ru.startandroid.importantdates.framework.Interactors;
import ru.startandroid.importantdates.framework.db.AppExecutors;

public class EventListViewModel extends ImportantDatesViewModel {
    private final Interactors interactors;
    private final MutableLiveData<List<Event>> events = new MutableLiveData<>();
    private final int month;

    public EventListViewModel(@NonNull Application application,
                              @NonNull Interactors interactors,
                              int month) {
        super(application, interactors);

        this.interactors = interactors;
        this.month = month;
    }

    public void loadEvents() {
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                events.postValue(interactors.getEventsByMonth().invoke(month));
            }
        });
    }

    public void addEvent(Event event) {
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                interactors.addEvent().invoke(event);
                loadEvents();
            }
        });
    }
}
