package ru.clevertec.ecl.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.domain.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
