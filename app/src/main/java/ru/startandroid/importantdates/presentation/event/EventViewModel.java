package ru.startandroid.importantdates.presentation.event;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.startandroid.importantdates.core.domain.Category;
import ru.startandroid.importantdates.core.domain.Event;
import ru.startandroid.importantdates.framework.ImportantDatesViewModel;
import ru.startandroid.importantdates.framework.Interactors;
import ru.startandroid.importantdates.framework.db.AppExecutors;

public class EventViewModel extends ImportantDatesViewModel {
    private final Interactors interactors;
    public final MutableLiveData<List<Category>> categories = new MutableLiveData<>();

    public EventViewModel(@NonNull Application application,
                          @NonNull Interactors interactors) {
        super(application, interactors);

        this.interactors = interactors;
    }

    public void loadCategories() {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            categories.postValue(interactors.getCategories().invoke());
        });
    }
}
