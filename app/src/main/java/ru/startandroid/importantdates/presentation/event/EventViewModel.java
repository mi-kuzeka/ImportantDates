package ru.startandroid.importantdates.presentation.event;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.startandroid.importantdates.core.domain.Category;
import ru.startandroid.importantdates.framework.ImportantDatesViewModel;
import ru.startandroid.importantdates.framework.Interactors;
import ru.startandroid.importantdates.framework.db.AppExecutors;

public class EventViewModel extends ImportantDatesViewModel {
    private final Interactors interactors;
    public final MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    public final MutableLiveData<Category> category = new MutableLiveData<>();

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

    public void getCategoryByName(String name) {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            category.postValue(interactors.getCategoryByName().invoke(name));
        });
    }

    public void addNewCategory(String categoryName) {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            interactors.addCategory().invoke(new Category(0, categoryName ));
        });
    }
}
