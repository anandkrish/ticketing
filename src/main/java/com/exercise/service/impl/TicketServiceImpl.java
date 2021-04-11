package com.exercise.service.impl;

import com.exercise.domain.Ticket;
import com.exercise.model.TicketSearchModel;
import com.exercise.repository.TicketRepository;
import com.exercise.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket createLogTicket(Ticket ticket) {
        ticket.setId("T1");
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


//
//    @Override
//    public Optional<Ticket> getTicketById(String id) {
//        Optional<Ticket> byId = ticketRepository.findById(id);
//        return Optional.ofNullable(byId).orElse(Optional.of(new Ticket()));
//    }

}
