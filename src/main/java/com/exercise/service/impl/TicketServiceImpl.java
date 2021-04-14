package com.exercise.service.impl;

import com.exercise.domain.Agent;
import com.exercise.domain.Ticket;
import com.exercise.model.TicketSearchModel;
import com.exercise.repository.TicketCustomRepository;
import com.exercise.repository.TicketRepository;
import com.exercise.service.TicketService;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class TicketServiceImpl implements TicketService {

    private final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketCustomRepository ticketCustomRepository;

    @Override
    public Ticket createLogTicket(Ticket ticket) {
        ticket.setId("T1");
        ticket.setCreatedDate(new Date());
        ticket.setModifiedDate(new Date());
        Ticket savedTicket = ticketRepository.save(ticket);
        return savedTicket;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public List<Ticket> getFilteredTickets(TicketSearchModel ticketSearchModel) {
        List<Ticket> filteredTickets = null;
        switch (ticketSearchModel.getSearchType()) {
            case BY_AGENT:
                filteredTickets = ticketRepository.findAllByAssignedTo(ticketSearchModel.getSearchValue());
                break;

            case BY_STATUS:
                filteredTickets =  ticketRepository.findAllByStatus(ticketSearchModel.getSearchValue());
                break;

            case BY_CUSTOMER:
                filteredTickets =   ticketRepository.findAllByUserId(ticketSearchModel.getSearchValue());
                break;
        }

        return filteredTickets;
    }

    @Override
    public Optional<Ticket> getTicketById(String id) {
        Optional<Ticket> byId = ticketRepository.findById(id);
        return Optional.ofNullable(byId).orElse(Optional.of(new Ticket()));
    }

    @Override
    public boolean editTicketDetails(Map<String,String> ticketMap) {
        Map<String, String> collect = ticketMap.entrySet().stream().filter((elem) ->
                !(elem.getKey().equalsIgnoreCase("status") || elem.getKey().equalsIgnoreCase("createdDate"))
        ).collect(Collectors.toMap(Map.Entry::getKey, el -> el.getValue()));

        return  ticketCustomRepository.updateTicket(collect);
    }

    @Override
    public boolean deleteTicket(String id) {
        boolean res = false;
        try{
            ticketRepository.deleteById(id);
            res = true;
        } catch( MongoException ex) {
            logger.error("Delete failed", ex);
        } catch (Exception e) {
            logger.error("Delete failed", e);
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean assignTicket(String id, Agent agent) {
        boolean assignedStatus = false;
        try {
            assignedStatus = ticketCustomRepository.updateTicket(id, agent);
        } catch( MongoException ex) {
            logger.error("Delete failed", ex);
        } catch (Exception e) {
            logger.error("Delete failed", e);
            e.printStackTrace();
        }
        return assignedStatus;
    }


//
//    @Override
//    public Optional<Ticket> getTicketById(String id) {
//        Optional<Ticket> byId = ticketRepository.findById(id);
//        return Optional.ofNullable(byId).orElse(Optional.of(new Ticket()));
//    }

}
