package ru.startandroid.importantdates.framework;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

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

}
