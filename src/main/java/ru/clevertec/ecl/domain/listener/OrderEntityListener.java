package ru.clevertec.ecl.domain.listener;

import jakarta.persistence.PrePersist;
import ru.clevertec.ecl.domain.entity.Order;

import java.math.BigDecimal;

public class OrderEntityListener {

    @PrePersist
    public void prePersist(Order order) {
        BigDecimal totalPrice = BigDecimal.valueOf(order.getGiftCertificates().stream()
                .mapToDouble(giftCertificate -> giftCertificate.getPrice().doubleValue())
                .sum());
        order.setTotalPrice(totalPrice);
    }

}
