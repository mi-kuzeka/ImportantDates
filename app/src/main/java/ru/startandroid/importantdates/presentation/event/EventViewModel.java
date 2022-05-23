package ru.startandroid.importantdates.presentation.event;

import android.app.Application;

import androidx.annotation.NonNull;

import ru.startandroid.importantdates.core.domain.Event;
import ru.startandroid.importantdates.framework.ImportantDatesViewModel;
import ru.startandroid.importantdates.framework.Interactors;

public class EventViewModel extends ImportantDatesViewModel {
    private final Interactors interactors;
    private Event event;

    public EventViewModel(@NonNull Application application,
                          @NonNull Interactors interactors,
                          Event event) {
        super(application, interactors);

        this.interactors = interactors;
        this.event = event;
    }
}
