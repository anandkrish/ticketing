package com.exercise.service;

import com.exercise.domain.Agent;
import com.exercise.domain.Ticket;
import com.exercise.exception.EmailNotSendExecption;
import com.exercise.model.TicketSearchModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface TicketService {

    public Ticket createLogTicket(Ticket ticket);

    public List<Ticket> getAllTickets();

    List<Ticket> getFilteredTickets(TicketSearchModel ticketSearchModel);

    Optional<Ticket> getTicketById(String id);

    boolean editTicketDetails(Map<String, String> ticket) throws EmailNotSendExecption, IOException;

    boolean deleteTicket(String id);

    boolean assignTicket(String id, Agent agent);
}

