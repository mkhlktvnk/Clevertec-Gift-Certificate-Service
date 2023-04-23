package ru.clevertec.ecl.integration.service.impl;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.domain.entity.Order;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.service.OrderService;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;
import ru.clevertec.ecl.service.message.MessageKey;
import ru.clevertec.ecl.service.message.MessagesSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderServiceImplTest extends BaseIntegrationTest {

    private static final Long CORRECT_USER_ID = 1L;

    private static final Long INCORRECT_USER_ID = 100L;

    private static final Long CORRECT_CERTIFICATE_ID = 2L;

    private static final Long INCORRECT_CERTIFICATE_ID = 101L;

    private static final Long CORRECT_ORDER_ID = 3L;

    private static final Long INCORRECT_ORDER_ID = 103L;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessagesSource messages;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findAllByUserIdShouldReturnCorrectCountOfOrders() {
        Pageable pageable = PageRequest.of(0, 3);

        List<Order> orders = orderService.findAllByUserId(CORRECT_USER_ID, pageable);

        assertThat(orders.size()).isEqualTo(3);
    }

    @Test
    void findAllByIdShouldThrowResourceNotFoundExceptionWhenUserIdIsIncorrect() {
        Pageable pageable = PageRequest.of(0, 3);

        assertThatThrownBy(() -> orderService.findAllByUserId(INCORRECT_USER_ID, pageable))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(messages.get(MessageKey.USER_NOT_FOUND));
    }

    @Test
    void findAllByUserIdShouldReturnEmptyResultWhenPageIsGreaterThanOrdersCount() {
        Pageable pageable = PageRequest.of(100, 3);

        List<Order> orders = orderService.findAllByUserId(CORRECT_USER_ID, pageable);

        assertThat(orders).isEmpty();
    }

    @Test
    void findByIdShouldReturnNotNullResult() {
        Order order = orderService.findById(CORRECT_ORDER_ID);

        assertThat(order).isNotNull();
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenOrderIdIsIncorrect() {
        assertThatThrownBy(() -> orderService.findById(INCORRECT_ORDER_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(messages.get(MessageKey.ORDER_NOT_FOUND));
    }

    @Test
    void makeOrderShouldMakeCorrectOrder() {
        Order order = orderService
                .makeOrder(CORRECT_USER_ID, CORRECT_CERTIFICATE_ID);

        assertThat(order.getId()).isNotNull();
        assertThat(order.getUser().getId())
                .isEqualTo(CORRECT_USER_ID);
        assertThat(order.getGiftCertificate().getId())
                .isEqualTo(CORRECT_CERTIFICATE_ID);
    }

    @Test
    void makeOrderShouldThrowResourceNotFoundExceptionWhenUserIdIsIncorrect() {
        assertThatThrownBy(() -> orderService.makeOrder(INCORRECT_USER_ID, CORRECT_CERTIFICATE_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(messages.get(MessageKey.USER_NOT_FOUND));
    }

    @Test
    void makeOrderShouldThrowResourceNotFoundExceptionWhenCertificateIdIsIncorrect() {
        assertThatThrownBy(() -> orderService.makeOrder(CORRECT_USER_ID, INCORRECT_CERTIFICATE_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(messages.get(MessageKey.GIFT_CERTIFICATE_NOT_FOUND));
    }

    @Test
    void deleteByIdShouldDeleteOrder() {
        orderService.deleteById(CORRECT_ORDER_ID);
        entityManager.flush();

        assertThatThrownBy(() -> orderService.findById(CORRECT_ORDER_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteByIdShouldThrowResourceNotFoundExceptionWhenOrderIdIsIncorrect() {
        assertThatThrownBy(() -> orderService.deleteById(INCORRECT_ORDER_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(messages.get(MessageKey.ORDER_NOT_FOUND));
    }
}