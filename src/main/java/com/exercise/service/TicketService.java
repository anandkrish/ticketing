package com.exercise.service;

import com.exercise.domain.Ticket;
import com.exercise.model.TicketSearchModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TicketService {

    public Ticket createLogTicket(Ticket ticket);

    public List<Ticket> getAllTickets();

    List<Ticket> getFilteredTickets(TicketSearchModel ticketSearchModel);

    Optional<Ticket> getTicketById(String id);
}

