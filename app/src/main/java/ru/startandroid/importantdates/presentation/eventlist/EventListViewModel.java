package ru.startandroid.importantdates.presentation.eventlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.startandroid.importantdates.core.domain.Category;
import ru.startandroid.importantdates.core.domain.Event;
import ru.startandroid.importantdates.framework.ImportantDatesViewModel;
import ru.startandroid.importantdates.framework.Interactors;
import ru.startandroid.importantdates.framework.db.AppExecutors;

public class EventListViewModel extends ImportantDatesViewModel {
    private final Interactors interactors;
    public final MutableLiveData<List<Event>> events = new MutableLiveData<>();

    public EventListViewModel(@NonNull Application application,
                              @NonNull Interactors interactors) {
        super(application, interactors);

        this.interactors = interactors;
    }

    public void loadEvents(int month) {
        AppExecutors.getInstance().getDiskIO().execute(() ->
                events.postValue(interactors.getEventsByMonth().invoke(month)));
    }

    public void addEvent(Event event) {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            interactors.addEvent().invoke(event);
            loadEvents(event.getMonth());
        });
    }

    public void addCategory(Category category) {
        AppExecutors.getInstance().getDiskIO().execute(() ->
                interactors.addCategory().invoke(category));
    }
}
