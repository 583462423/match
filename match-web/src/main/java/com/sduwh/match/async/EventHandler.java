package com.sduwh.match.async;

import java.util.List;

/**
 * Created by qxg on 17-8-26.
 */
public interface EventHandler {
    void doHandle(EventModel eventModel);
    List<EventType> getSupportEventTypes();
}
