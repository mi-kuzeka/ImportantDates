package ru.startandroid.importantdates.framework;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

public class ImportantDatesViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private Interactors dependencies;

    public final void inject(Application application, Interactors dependencies) {
        this.application = application;
        this.dependencies = dependencies;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (ImportantDatesViewModel.class.isAssignableFrom(modelClass)) {
            try {
                return modelClass.getConstructor(Application.class, Interactors.class)
                        .newInstance(application, dependencies);
            } catch (IllegalAccessException | InstantiationException |
                    InvocationTargetException | NoSuchMethodException e) {
                throw new IllegalStateException("ViewModel must extend ImportantDatesViewModel");
            }
        } else {
            throw new IllegalStateException("ViewModel must extend ImportantDatesViewModel");
        }
    }
}
