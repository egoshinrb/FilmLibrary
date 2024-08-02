package com.example.filmlibrary.repository;


import com.example.filmlibrary.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository
        extends GenericRepository<Order> {
}