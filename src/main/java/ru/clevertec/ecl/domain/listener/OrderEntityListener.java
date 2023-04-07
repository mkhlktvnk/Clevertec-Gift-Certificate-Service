package ru.clevertec.ecl.domain.listener;

import jakarta.persistence.PrePersist;
import ru.clevertec.ecl.domain.entity.Order;

import java.math.BigDecimal;

public class OrderEntityListener {

    @PrePersist
    void setTotalPrice(Order order) {
        BigDecimal totalPrice = order.getGiftCertificate().getPrice();
        order.setTotalPrice(totalPrice);
    }

}
