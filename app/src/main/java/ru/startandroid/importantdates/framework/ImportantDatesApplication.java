package ru.startandroid.importantdates.framework;

import android.app.Application;

import ru.startandroid.importantdates.core.data.CategoryRepository;
import ru.startandroid.importantdates.core.data.EventRepository;

public class ImportantDatesApplication extends Application {
    private EventRepository eventRepository;
    private CategoryRepository categoryRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        eventRepository = new EventRepository(new RoomEventDataSource(this));
        categoryRepository = new CategoryRepository(new RoomCategoryDataSource(this));
    }


}
