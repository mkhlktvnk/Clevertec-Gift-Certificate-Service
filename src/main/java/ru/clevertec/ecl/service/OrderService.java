package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.domain.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAllByUserId(long userId, Pageable pageable);

    Order findById(long id);

    Order makeOrder(long userId, long certificateId);

    void deleteById(long id);
}