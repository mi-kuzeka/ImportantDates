package ru.startandroid.importantdates.framework;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import ru.startandroid.importantdates.core.domain.Category;
import ru.startandroid.importantdates.core.domain.Event;
import ru.startandroid.importantdates.framework.db.AppExecutors;

public class ImportantDatesViewModel extends AndroidViewModel {
    private final ImportantDatesApplication application;
    private final Interactors interactors;

    @NonNull
    public final ImportantDatesApplication getApplication() {
        return this.application;
    }

    @NonNull
    protected final Interactors getInteractors() {
        return this.interactors;
    }

    public ImportantDatesViewModel(@NonNull Application application,
                                   @NonNull Interactors interactors) {
        super(application);
        this.interactors = interactors;
        this.application = this.getApplication();
    }

    public void addEvent(Event event) {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            interactors.addEvent().invoke(event);
        });
    }

    public void addCategory(Category category) {
        AppExecutors.getInstance().getDiskIO().execute(() ->
                interactors.addCategory().invoke(category));
    }

}
