package ru.startandroid.importantdates.framework;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.startandroid.importantdates.core.data.EventDataSource;
import ru.startandroid.importantdates.core.domain.Category;
import ru.startandroid.importantdates.core.domain.Event;
import ru.startandroid.importantdates.core.domain.EventDate;
import ru.startandroid.importantdates.framework.db.EventDao;
import ru.startandroid.importantdates.framework.db.EventEntity;
import ru.startandroid.importantdates.framework.db.ImportantDatesDatabase;

public class RoomEventDataSource implements EventDataSource {
    private final EventDao eventDao;
    private final RoomCategoryDataSource roomCategoryDataSource;

    public RoomEventDataSource(Context context) {
        eventDao = ImportantDatesDatabase.getInstance(context).eventDao();
        roomCategoryDataSource = new RoomCategoryDataSource(context);
    }

    @Override
    public void add(Event event) {
        eventDao.addEvent(getEventEntity(event));
    }

    @Override
    public List<Event> readAll() {
        List<EventEntity> eventEntities = eventDao.getAllEvents();
        List<Event> events = new ArrayList<>();

        for (EventEntity eventEntity : eventEntities)
            events.add(getEvent(eventEntity));

        return events;
    }

    @Override
    public List<Event> readByMonth(int month) {
        List<EventEntity> eventEntities = eventDao.getEventsByMonth(month);
        List<Event> events = new ArrayList<>();

        for (EventEntity eventEntity : eventEntities)
            events.add(getEvent(eventEntity));

        return events;
    }

    @Override
    public Event readById(int id) {
        EventEntity eventEntity = eventDao.getEventById(id);
        return getEvent(eventEntity);
    }

    @Override
    public void update(Event event) {
        eventDao.updateEvent(getEventEntity(event));
    }

    @Override
    public void remove(Event event) {
        eventDao.deleteEvent(getEventEntity(event));
    }

    /**
     * Cast {@link EventEntity} object to {@link Event} object
     *
     * @param eventEntity {@link EventEntity} object
     * @return {@link Event} object
     */
    private Event getEvent(EventEntity eventEntity) {
        EventDate eventDate = new EventDate(
                eventEntity.month,
                eventEntity.day,
                eventEntity.year);
        Category eventCategory =
                roomCategoryDataSource.readById(eventEntity.category_id);

        return new Event(
                eventEntity.id,
                eventEntity.name,
                eventDate,
                eventCategory,
                eventEntity.notes,
                ImageConverter.getBitmap(eventEntity.image));
    }

    /**
     * Cast {@link Event} object to {@link EventEntity} object
     *
     * @param event {@link Event} object
     * @return {@link EventEntity} object
     */
    private EventEntity getEventEntity(Event event) {
        return new EventEntity(
                event.getId(),
                event.getName(),
                event.getMonth(),
                event.getDay(),
                event.getYear(),
                event.getNotes(),
                event.getCategory().getId(),
                ImageConverter.getByteArray(event.getBitmapImage())
        );
    }
}
