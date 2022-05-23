package ru.startandroid.importantdates.framework;

import android.app.Application;

import ru.startandroid.importantdates.core.data.CategoryRepository;
import ru.startandroid.importantdates.core.data.EventRepository;
import ru.startandroid.importantdates.core.interactors.*;

public class ImportantDatesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        EventRepository eventRepository =
                new EventRepository(new RoomEventDataSource(this));
        CategoryRepository categoryRepository =
                new CategoryRepository(new RoomCategoryDataSource(this));

        ImportantDatesViewModelFactory.getInstance().inject(
                this,
                new Interactors(
                        new AddCategory(categoryRepository),
                        new AddEvent(eventRepository),
                        new DeleteCategory(categoryRepository),
                        new DeleteEvent(eventRepository),
                        new GetCategories(categoryRepository),
                        new GetCategoryById(categoryRepository),
                        new GetCategoryByName(categoryRepository),
                        new GetEventById(eventRepository),
                        new GetEvents(eventRepository),
                        new GetEventsByMonth(eventRepository),
                        new UpdateCategory(categoryRepository),
                        new UpdateEvent(eventRepository)
                ));
    }

}
