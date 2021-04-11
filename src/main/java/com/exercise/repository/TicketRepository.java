package com.exercise.repository;

import com.exercise.domain.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findAllByAssignedTo(String assignedTo);

    List<Ticket> findAllByStatus(String status);

    List<Ticket> findAllByUserId(String userId);
}
