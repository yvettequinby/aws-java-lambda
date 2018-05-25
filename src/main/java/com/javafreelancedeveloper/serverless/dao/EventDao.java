package com.javafreelancedeveloper.serverless.dao;


import java.util.List;
import java.util.Optional;

import com.javafreelancedeveloper.serverless.domain.Event;


public interface EventDao {

    List<Event> findAllEvents();

    List<Event> findEventsByCity(String city);

    List<Event> findEventsByTeam(String team);

    Optional<Event> findEventByTeamAndDate(String team, Long eventDate);

    void saveOrUpdateEvent(Event event);

    void deleteEvent(String team, Long eventDate);

}
