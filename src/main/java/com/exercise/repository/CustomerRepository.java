package com.exercise.repository;

import com.exercise.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer,String> {
    Customer findByUserId(String userId);
}
