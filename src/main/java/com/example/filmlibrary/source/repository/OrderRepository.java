package com.example.filmlibrary.source.repository;


import com.example.filmlibrary.source.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository
        extends GenericRepository<Order> {
    Page<Order> getOrderByUserId(Long id, Pageable pageRequest);
}