package com.exercise.controller;

import com.exercise.domain.Status;
import com.exercise.domain.Ticket;
import com.exercise.model.TicketSearchModel;
import com.exercise.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class TicketController {

    private final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/logTicket", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> createTicket(@RequestBody Ticket ticket) {
        ticketService.createLogTicket(ticket);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllTickets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> allTickets = ticketService.getAllTickets();
        return new ResponseEntity<>(allTickets,HttpStatus.OK);
    }


    @RequestMapping(value = "/getTicketBy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ticket>> getFilteresTickets(TicketSearchModel ticketSearchModel) {

        Status[] possibleValues = Status.class.getEnumConstants();
        List<String> possibleStatusValues = Arrays.stream(possibleValues).map(val -> val.getStatus()).collect(Collectors.toList());
        if(ticketSearchModel.getSearchType().equals("BY_STATUS") &&
                (!possibleStatusValues.contains(ticketSearchModel.getSearchValue()))) {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        } else {
            List<Ticket> filteredTickets = ticketService.getFilteredTickets(ticketSearchModel);
            return new ResponseEntity<>(filteredTickets,HttpStatus.OK);
        }


    }

    @RequestMapping(value = "/getTicketBy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ticket>> getFilteresTickets() {
        List<Ticket> filteredTickets = ticketService.getAllTickets();
        return new ResponseEntity<>(filteredTickets,HttpStatus.OK);
    }

    @RequestMapping(value = "/getTicketById/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> getTicketById(@PathVariable("id") String id) {
        Optional<Ticket> ticket  = ticketService.getTicketById(id);
        if(ticket.isPresent()) {
            return new ResponseEntity<>(ticket.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}
