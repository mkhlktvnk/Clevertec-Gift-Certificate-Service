package ru.clevertec.ecl.web.controller;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.domain.entity.Order;
import ru.clevertec.ecl.service.OrderService;
import ru.clevertec.ecl.web.mapper.OrderMapper;
import ru.clevertec.ecl.web.model.OrderModel;

import java.util.List;

@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @GetMapping("/users/{id}/orders")
    public List<OrderModel> findAllByUserId(@PathVariable Long id, @PageableDefault Pageable pageable) {
        List<Order> orders = orderService.findAllByUserId(id, pageable);
        return orderMapper.mapToModel(orders);
    }

    @PostMapping("/users/{userId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderModel makeOrder(@PathVariable Long userId, @RequestParam("certificateId") Long certificateId) {
        Order order = orderService.makeOrder(userId, certificateId);
        return orderMapper.mapToModel(order);
    }
}
