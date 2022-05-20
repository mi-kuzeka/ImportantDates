package ru.startandroid.importantdates.framework;

import android.content.Context;

import java.util.List;

import ru.startandroid.importantdates.core.data.EventDataSource;
import ru.startandroid.importantdates.core.domain.Event;
import ru.startandroid.importantdates.framework.db.EventDao;
import ru.startandroid.importantdates.framework.db.EventEntity;
import ru.startandroid.importantdates.framework.db.ImportantDatesDatabase;

public class RoomEventDataSource implements EventDataSource {
    private Context context;
    private EventDao eventDao = ImportantDatesDatabase.getInstance(context).eventDao();

    public RoomEventDataSource(Context context) {
        this.context = context;
    }

    @Override
    public void add(Event event) {
        eventDao.addEvent(new EventEntity(
                event.getId(),
                event.getName(),
                event.getMonth(),
                event.getDay(),
                event.getYear(),
                event.getNotes(),
                event.getCategory().getId(),
                ImageConverter.getByteArray(event.getBitmapImage())
        ));
    }

    @Override
    public List<Event> readAll() {
        return null;
    }

    @Override
    public List<Event> readByMonth(int month) {
        return null;
    }

    @Override
    public Event readById(int id) {
        return null;
    }

    @Override
    public void update(Event event) {

    }

    @Override
    public void remove(Event event) {

    }
}
