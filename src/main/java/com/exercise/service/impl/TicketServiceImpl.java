package com.exercise.service.impl;

import com.exercise.domain.Agent;
import com.exercise.domain.Customer;
import com.exercise.domain.Ticket;
import com.exercise.exception.EmailNotSendExecption;
import com.exercise.model.TicketSearchModel;
import com.exercise.repository.CustomerRepository;
import com.exercise.repository.TicketCustomRepository;
import com.exercise.repository.TicketRepository;
import com.exercise.service.TicketService;
import com.exercise.utils.SendEmailUtil;
import com.exercise.utils.TicketsConstants;
import com.mongodb.MongoException;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class TicketServiceImpl implements TicketService {

    private final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketCustomRepository ticketCustomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketCustomRepository ticketCustomRepository, CustomerRepository customerRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketCustomRepository = ticketCustomRepository;
        this.customerRepository = customerRepository;
    }

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
        return Optional.of(byId).orElse(Optional.of(new Ticket()));
    }

    @Override
    public boolean editTicketDetails(Map<String,String> ticketMap) {
        Map<String, String> collect = ticketMap.entrySet().stream().filter((elem) ->
                !(elem.getKey().equalsIgnoreCase("status") || elem.getKey().equalsIgnoreCase("createdDate"))
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        boolean res = ticketCustomRepository.updateTicket(collect);
        if(res) {
            CompletableFuture.runAsync(()  -> {
                try {
                    sendEmailToCustomer(ticketMap);
                } catch (EmailNotSendExecption emailNotSendExecption) {
                    emailNotSendExecption.printStackTrace();
                }
            });
        }
        return res;
    }

    private void sendEmailToCustomer(Map<String,String> ticket) throws EmailNotSendExecption {
        if(!getCustomerEmail(ticket.get("userId")).isEmpty()) {
            try {
                Email from = new Email(TicketsConstants.FROM_EMAIL);
                String subject = ticket.get("Id")+"|" + TicketsConstants.SUBJECT;
                Email to = new Email();
                Content content = new Content("text/plain", ticket.get("comments") + "\n" + ticket.get("resolutionInfo"));
                Mail mail = new Mail(from, subject, to, content);
                SendEmailUtil.send(mail);
            } catch(IOException ex) {
                logger.error("ERROR sending email",ex);
            }

        } else {
            throw new EmailNotSendExecption("CUSTOMER_EMAIL_NOT_FOUND");
        }
    }

    private String getCustomerEmail(String userId) {
        String email;
        Customer byUserId = customerRepository.findByUserId(userId);
        email = Optional.ofNullable(byUserId).isPresent() ? byUserId.getEmailId() : "";
       return email;
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
