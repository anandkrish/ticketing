package com.exercise.repository;

import com.exercise.domain.Agent;
import com.exercise.domain.Ticket;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Repository
public class TicketCustomRepository {

    @Autowired
    private MongoOperations mongoOperation;


    public boolean updateTicket(Map<String,String> ticketMap) {
        Query query = new Query();
        query.addCriteria(Criteria.where("Id").exists(true));
        Update updateval = new Update();
        ticketMap.entrySet().forEach(elem -> {
            if(elem.getKey().equals(new Ticket().getClass().getDeclaredFields())) {
                updateval.set(elem.getKey(),elem.getValue());

            }
            updateval.set("modifiedDate",new Date());
        });


        UpdateResult updateResult = mongoOperation.updateMulti(query, updateval, Ticket.class);
       return updateResult.getModifiedCount() == 1 ? true : false;

    }

    public boolean updateTicket(String id, Agent agent) {
        Query query = new Query();
        query.addCriteria(Criteria.where("Id").exists(true));
        Update updateval = new Update();
        updateval.set("assignedTo",agent.getAgentId());
        updateval.set("modifiedDate",new Date());
        UpdateResult updateResult = mongoOperation.updateMulti(query, updateval, Ticket.class);
        return updateResult.getModifiedCount() == 1 ? true : false;
    }
}
