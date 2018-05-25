package com.javafreelancedeveloper.serverless.dao;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.javafreelancedeveloper.serverless.domain.Event;
import com.javafreelancedeveloper.serverless.manager.DynamoDBManager;

import org.apache.log4j.Logger;

import java.util.*;


public class DynamoDBEventDao implements EventDao {

    private static final Logger log = Logger.getLogger(DynamoDBEventDao.class);

    private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

    private static volatile DynamoDBEventDao instance;


    private DynamoDBEventDao() { }

    public static DynamoDBEventDao instance() {

        if (instance == null) {
            synchronized(DynamoDBEventDao.class) {
                if (instance == null)
                    instance = new DynamoDBEventDao();
            }
        }
        return instance;
    }

    @Override
    public List<Event> findAllEvents() {
        return mapper.scan(Event.class, new DynamoDBScanExpression());
    }

    @Override
    public List<Event> findEventsByCity(String city) {

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue().withS(city));

        DynamoDBQueryExpression<Event> query = new DynamoDBQueryExpression<Event>()
                                                    .withIndexName(Event.CITY_INDEX)
                                                    .withConsistentRead(false)
                                                    .withKeyConditionExpression("city = :v1")
                                                    .withExpressionAttributeValues(eav);

        return mapper.query(Event.class, query);
    
    }

    @Override
    public List<Event> findEventsByTeam(String team) {

        DynamoDBQueryExpression<Event> homeQuery = new DynamoDBQueryExpression<>();
        Event eventKey = new Event();
        eventKey.setHomeTeam(team);
        homeQuery.setHashKeyValues(eventKey);
        List<Event> homeEvents = mapper.query(Event.class, homeQuery);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue().withS(team));
        DynamoDBQueryExpression<Event> awayQuery = new DynamoDBQueryExpression<Event>()
                                                        .withIndexName(Event.AWAY_TEAM_INDEX)
                                                        .withConsistentRead(false)
                                                        .withKeyConditionExpression("awayTeam = :v1")
                                                        .withExpressionAttributeValues(eav);

        List<Event> awayEvents = mapper.query(Event.class, awayQuery);

        // need to create a new list because PaginatedList from query is immutable
        List<Event> allEvents = new LinkedList<>();
        allEvents.addAll(homeEvents);
        allEvents.addAll(awayEvents);
        allEvents.sort( (e1, e2) -> e1.getEventDate() <= e2.getEventDate() ? -1 : 1 );

        return allEvents;
    }

    @Override
    public Optional<Event> findEventByTeamAndDate(String team, Long eventDate) {

        Event event = mapper.load(Event.class, team, eventDate);

        return Optional.ofNullable(event);
    }

    @Override
    public void saveOrUpdateEvent(Event event) {

        mapper.save(event);
    }

    @Override
    public void deleteEvent(String team, Long eventDate) {

        Optional<Event> oEvent = findEventByTeamAndDate(team, eventDate);
        if (oEvent.isPresent()) {
            mapper.delete(oEvent.get());
        }
        else {
            log.error("Could not delete event, no such team and date combination");
            throw new IllegalArgumentException("Delete failed for nonexistent event");
        }
    }
}
